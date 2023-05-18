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
    const workerInstanceIds = this.loadBalancerService.getInstanceIds("WORKER-MICROSERVICE");
    console.log("eureka" +JSON.stringify(workerInstanceIds))
  }
}
