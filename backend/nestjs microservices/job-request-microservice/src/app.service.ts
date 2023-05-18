import { Inject, Injectable } from '@nestjs/common';
import { JobRequestDto } from './dto/create-job-request.dto';
import { Model } from 'mongoose';
import { JobRequest } from './schemas/job-request.schema';
import { InjectModel } from '@nestjs/mongoose';
import { Eureka } from 'eureka-js-client';


@Injectable()
export class AppService {

  constructor(
    @InjectModel(JobRequest.name) private readonly jobRequestModel:Model<JobRequest>,
    @Inject(Eureka) private eureka: Eureka
  ){}
  getHello(): string {
   
    return 'Hello World!';
  }

  
  async create({companyId,workerIds}:JobRequestDto){
    console.log("eureka" +this.eureka)
  }
}
