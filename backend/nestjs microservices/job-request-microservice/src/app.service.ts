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
import { JobRequestPayementResponse } from './types/job-request-payement-response';

@Injectable()
export class AppService {
  constructor(
    @InjectModel(JobRequest.name)
    private readonly jobRequestModel: Model<JobRequest>,
    @InjectModel(Contract.name)
    private readonly contractModel: Model<ContractDocument>,
    private loadBalancerService: LoadBalancerService,
    private readonly httpService: HttpService,
  ) {}
  getHello(): string {
    return 'Hello World!';
  }

  async create({ workers }: JobRequestDto, companyId: string) {
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
      if (!res1.data) {
        throw new BadRequestException('company does not exist');
      }

      return await this.jobRequestModel.create({
        companyId: companyId,
        workers: workers.map((w) => ({
          workerId: w.workerId,
          nbHours: w.nbHours,
          publicPrice: workersStatus.find((ws) => ws.workerId === w.workerId)
            ?.publicPrice,
        })),
      });
    } catch (err) {
      console.error(err);
      throw new InternalServerErrorException(err);
    }
  }
  // async reviewJobRequest(
  //   jobRequestId: string,
  //   {  removedWorkerIds,observation }: ReviewJobRequestDto,
  // ) {
  //   const jobRequest = await this.jobRequestModel.findById(jobRequestId).exec();
  //   if(!jobRequest){
  //     throw new BadRequestException("could not find the job request");
  //   }
  //   removedWorkerIds.forEach(rw=>{
  //     if(!jobRequest.workers.find(w=>w.workerId === rw)){
  //       throw new BadRequestException("worker not found in job request")
  //     }
  //   })
  //   if(jobRequest.workers.length  === 1 && removedWorkerIds.length >=1){
  //     throw new BadRequestException("can't remove the worker cuz its the only worker")
  //   }

  //   jobRequest.workers.forEach((w,i,wArr)=>{
  //     if(removedWorkerIds.includes(w.workerId)){
  //       wArr[i].removed = new Date();
  //     }
  //   })
  //   console.log("reviewing ",JSON.stringify(jobRequest))
  //   return this.jobRequestModel.updateOne(
  //     { _id: jobRequestId },
  //     { $set: {status:JobRequestStatus.REVIEWED,workers:jobRequest.workers,observation} },
  //     { new: true },
  //   ).exec();
  // }

  // async accepteJobRequest(jobRequestId:string,{accepted}:AcceptJobRequestDto,companyId:string){
  //   const jobRequest = await this.jobRequestModel.findById(jobRequestId).exec()
  //   if(!jobRequest){
  //     throw new BadRequestException("could not find job request");
  //   }
  //   if(companyId !== jobRequest.companyId){
  //     throw new UnauthorizedException("job request is not yours");
  //   }
  //   if(jobRequest.status !== JobRequestStatus.REVIEWED){
  //     throw new BadRequestException("job request not reviewed yet by an admin")
  //   }

  //   if(accepted){

  //   const payementMicroserviceInstanceId = this.loadBalancerService.getInstanceId(
  //     'PAYMENT-MICROSERVICE',
  //   );
  //     const newBillingurl =
  //     'http://' + payementMicroserviceInstanceId + `/payment/new-billing`;
  //     console.log("http request to : "+newBillingurl)
  //     let total = 0;
  //     jobRequest.workers.forEach(w=>{
  //       if(w.removed) return;
  //       total+=w.nbHours*w.publicPrice;
  //     })
  //     try{
  //       const res1 = await this.httpService.axiosRef.post(newBillingurl,{
  //         idCompany:jobRequest.companyId,
  //         jobRequestId:jobRequest.id,
  //         billAmount:total,

  //       });
  //       await this.jobRequestModel.updateOne({_id:jobRequestId},{
  //         $set:{
  //           status:accepted ?JobRequestStatus.ACCEPTED:JobRequestStatus.REJECTED
  //         }
  //       }).exec()
  //       const contracts = jobRequest.workers.map(worker => ({
  //         companyId: jobRequest.companyId,
  //         workerId: worker.workerId,
  //         publicPrice:worker.publicPrice,
  //         nbHours:worker.nbHours,
  //         createdAt:new Date()
  //       }));

  //       return  await this.contractModel.insertMany(contracts)
  //     }catch(err){
  //       throw err;
  //     }
  //     }
  //   else {
  //     await this.jobRequestModel.updateOne({_id:jobRequestId},{
  //       $set:{
  //         status:accepted ?JobRequestStatus.ACCEPTED:JobRequestStatus.REJECTED
  //       }
  //     }).exec()
  //     return "rejected"
  //   }
  // }
  async getJobRequests() {
    const jobRequests = await this.jobRequestModel.find().exec();
    const payementMicroserviceInstanceId =
      this.loadBalancerService.getInstanceId('PAYMENT-MICROSERVICE');
    const billingUrl =
      'http://' +
      payementMicroserviceInstanceId +
      `/payment?jobRequestIds=${jobRequests.map((j) => j.id).join(',')}`;
    console.log('http request to : ' + billingUrl);
    let total = 0;

    try {
      const res1 = await this.httpService.axiosRef.get(billingUrl);
      const jobRequestPayment = res1.data as JobRequestPayementResponse[];

      return jobRequests.map((j) => ({
        id: j.id,
        workers: j.workers,
        payement: jobRequestPayment.find((p) => p.jobRequestId === j.id),
      }));
    } catch (err) {
      throw err;
    }
  }
  async getAllContracts() {
    return this.contractModel.find();
  }

  async getCompanyJobRequests(userId: string) {
    return this.jobRequestModel.find({ companyId: userId });
  }
  async getJobRequestDetails(jobRequestId: string) {
    return this.jobRequestModel.findOne({ id: jobRequestId });
  }
  async accepteJobRequstAdmin(
    { accepted ,jobRequestId}: AcceptJobRequestDto,
  ) {
    const jobRequest = await this.jobRequestModel.findById(jobRequestId).exec();
    if (!jobRequest) {
      throw new BadRequestException('job request not found');
    }

    if (accepted) {
      const payementMicroserviceInstanceId =
        this.loadBalancerService.getInstanceId('PAYMENT-MICROSERVICE');
      const newBillingurl =
        'http://' + payementMicroserviceInstanceId + `/payment/new-billing`;
      console.log('http request to : ' + newBillingurl);
      let total = 0;

      jobRequest.workers.forEach((w) => {
        total += w.nbHours * w.publicPrice;
      });
      try {
        const res1 = await this.httpService.axiosRef.post(newBillingurl, {
          idCompany: jobRequest.companyId,
          jobRequestId: jobRequest.id,
          billAmount: total,
        });
        await this.jobRequestModel
          .updateOne(
            { _id: jobRequestId },
            {
              $set: {
                status:  JobRequestStatus.ACCEPTED
                  ,
              },
            },
          )
          .exec();
        const contracts = jobRequest.workers.map((worker) => ({
          companyId: jobRequest.companyId,
          workerId: worker.workerId,
          publicPrice: worker.publicPrice,
          nbHours: worker.nbHours,
          createdAt: new Date(),
        }));

        return await this.contractModel.insertMany(contracts);
      } catch (err) {
        throw err;
      }
    } else {
      await this.jobRequestModel
        .updateOne(
          { _id: jobRequestId },
          {
            $set: {
              status:JobRequestStatus.REJECTED,
            },
          },
        )
        .exec();
      return 'rejected';
    }
  }
}
