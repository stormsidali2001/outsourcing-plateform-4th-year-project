import {
  Inject,
  Injectable,
  BadRequestException,
  InternalServerErrorException,
  UnauthorizedException,
} from '@nestjs/common';
import { JobRequestDto } from './dto/create-job-request.dto';
import { Model } from 'mongoose';
import { JobRequest } from './schemas/job-request.schema';
import { InjectModel } from '@nestjs/mongoose';
import { Eureka } from 'eureka-js-client';
import { DiscoveryService, EurekaModule } from 'nestjs-eureka';
import { HttpService } from '@nestjs/axios';
import { AxiosRequestConfig } from 'axios';
import { LoadBalancerService } from './eureka/LoadBalancer.service';
import { WorkerStatusResponse } from './types/worker-status-reponse';
import { ApiInternalServerErrorResponse } from '@nestjs/swagger';
import { ReviewJobRequestDto } from './dto/review-job-request.dto';
import { JobRequestStatus } from './types/job-request-status.enum';
import { Contract, ContractDocument } from './schemas/contract.schema';
import { ContractStatus } from './types/contract-status.enum';
import { AcceptJobRequestDto } from './dto/accept-job-request.dto';

@Injectable()
export class AppService {
  constructor(
    @InjectModel(JobRequest.name)
    private readonly jobRequestModel: Model<JobRequest>,
    @InjectModel(Contract.name)private readonly contractModel: Model<ContractDocument>,
    private loadBalancerService: LoadBalancerService,
    private readonly httpService: HttpService,
  ) {}
  getHello(): string {
    return 'Hello World!';
  }

  async create({ workers }: JobRequestDto,companyId:string) {
    const workerInstanceId = this.loadBalancerService.getInstanceId(
      'WORKER-MICROSERVICE',
    );

    try {
      const workerIds = workers.map((w) => w.workerId);
      const url =
        'http://' +
        workerInstanceId +
        `/workers-exist?workerIds=${workerIds.join(',')}`;
      console.log('full url : ' + url);
      const res = await this.httpService.axiosRef.get(url);
      const workersStatus: WorkerStatusResponse[] = res.data;
      console.log('worker status', workersStatus);
      workersStatus.forEach((w) => {
        if (!w.exists || w.status !== 'APPROVED') {
          throw new BadRequestException(
            'one of the requested workers does not exist',
          );
        }
      });
      console.log('workersStatus ', workersStatus);

      const companyInstanceId = this.loadBalancerService.getInstanceId(
        'COMPANY-MICROSERVICE',
      );
      const companyUrl =
        'http://' + companyInstanceId + `/company-exists/${companyId}`;
      const res1 = await this.httpService.axiosRef.get(companyUrl);
      console.log('company ', res1.data);
      if(!res1.data){
        throw new BadRequestException("company does not exist")
      }

      return await this.jobRequestModel.create({
        companyId: companyId,
        workers: workers.map((w) => ({
          workerId: w.workerId,
          nbHours:w.nbHours,
          publicPrice: workersStatus.find((ws) => ws.workerId === w.workerId)
            ?.publicPrice,
        })),
      });
    } catch (err) {
      console.error(err);
      throw new InternalServerErrorException(err);
    }
  }
  async reviewJobRequest(
    jobRequestId: string,
    {  removedWorkerIds }: ReviewJobRequestDto,
  ) {
    const jobRequest = await this.jobRequestModel.findById(jobRequestId).exec();
    if(!jobRequest){
      throw new BadRequestException("could not find the job request");
    }
    if(jobRequest.workers.length  === 1 && removedWorkerIds.length >=1){
      throw new BadRequestException("can't remove the worker cuz its the only worker")
    }
    return this.jobRequestModel.updateOne(
      { _id: jobRequestId, 'workers.workerId': { $in: removedWorkerIds } },
      { $set: { 'workers.$.removed': new Date() ,status:JobRequestStatus.REVIEWED} },
      { new: true },
    ).exec();
  }

  async accepteJobRequest(jobRequestId:string,{accepted}:AcceptJobRequestDto,companyId:string){
    const jobRequest = await this.jobRequestModel.findById(jobRequestId).exec()
    if(!jobRequest){
      throw new BadRequestException("could not find job request");
    }
    if(companyId !== jobRequest.companyId){
      throw new UnauthorizedException("job request is not yours");
    }
    if(jobRequest.status !== JobRequestStatus.REVIEWED){
      throw new BadRequestException("job request not reviewed yet by an admin")
    }

    await this.jobRequestModel.updateOne({_id:jobRequestId},{
      $set:{
        status:accepted ?JobRequestStatus.ACCEPTED:JobRequestStatus.REJECTED
      }
    }).exec()
    const contracts = jobRequest.workers.map(worker => ({
      companyId: jobRequest.companyId,
      workerId: worker.workerId,
      publicPrice:worker.publicPrice,
      nbHours:worker.nbHours,
      createdAt:new Date()
    }));
  
    if(accepted) return await this.contractModel.insertMany(contracts)
    else return "rejected"
  } 
  async getJobRequests(){
    const jobRequests = await  this.jobRequestModel.find().exec()

    const payementMicroserviceInstanceId = this.loadBalancerService.getInstanceId(
      'PAYMENT-MICROSERVICE',
    );
    return jobRequests;
  }
}
