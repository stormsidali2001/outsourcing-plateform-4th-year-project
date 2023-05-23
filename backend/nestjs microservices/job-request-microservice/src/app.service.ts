import { Inject, Injectable } from '@nestjs/common';
import { JobRequestDto } from './dto/create-job-request.dto';
import { Model } from 'mongoose';
import { JobRequest } from './schemas/job-request.schema';
import { InjectModel } from '@nestjs/mongoose';
import { Eureka } from 'eureka-js-client';
import { DiscoveryService ,EurekaModule} from 'nestjs-eureka';
import { HttpService } from '@nestjs/axios';
import { AxiosRequestConfig } from 'axios';
import { LoadBalancerService } from './eureka/LoadBalancer.service';
import { WorkerStatusResponse } from './types/worker-status-reponse';


@Injectable()
export class AppService {

  constructor(
    @InjectModel(JobRequest.name) private readonly jobRequestModel:Model<JobRequest>,
    private loadBalancerService:LoadBalancerService,
    private readonly httpService:HttpService
  ){}
  getHello(): string {
   
    return 'Hello World!';
  }

  
  async create({companyId,workerIds}:JobRequestDto){
    const workerInstanceId = this.loadBalancerService.getInstanceId("WORKER-MICROSERVICE");
    
    try{
      const url = "http://"+workerInstanceId+`/workers-exist?workerIds=${workerIds.join(",")}`;
      console.log("full url : "+url)
      const res =  await this.httpService.axiosRef.get(url)
      const workersStatus:WorkerStatusResponse[] =  res.data;

      console.log("workersStatus ",workersStatus)

      const companyInstanceId = this.loadBalancerService.getInstanceId("COMPANY-MICROSERVICE");
      const companyUrl =  "http://"+companyInstanceId+`/company-exists/${companyId}`;
      const res1 =  await this.httpService.axiosRef.get(companyUrl)
      console.log("company ",res1.data)

    }catch(err){
      console.error(err)
    }
   
  }
}