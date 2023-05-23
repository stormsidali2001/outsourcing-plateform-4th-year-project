import { Body, Controller, Get , Post,Param, ParseBoolPipe} from '@nestjs/common';
import { AppService } from './app.service';
import { JobRequestDto } from './dto/create-job-request.dto';
import { ReviewJobRequestDto } from './dto/review-job-request.dto';
import { AcceptJobRequestDto } from './dto/accept-job-request.dto';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}


  @Post()
  async create(@Body() data:JobRequestDto){
    return this.appService.create(data);
  }
  
  @Post("review-job-request/:id")
  async reviewJobRequest(@Param("id")  jobRequestId:string , @Body() data:ReviewJobRequestDto){
    return await this.appService.reviewJobRequest(jobRequestId,data);
  }
  @Post("accept-job-request/:id")
  async accepteJobRequst(@Param("id")  jobRequestId:string,@Body() data:AcceptJobRequestDto){
    return await this.appService.accepteJobRequest(jobRequestId,data)
  }
  
}
