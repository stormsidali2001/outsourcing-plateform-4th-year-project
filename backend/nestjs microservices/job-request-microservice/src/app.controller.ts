import { Body, Controller, Get , Post,Param, ParseBoolPipe, Header, Req, BadRequestException, UnauthorizedException, ForbiddenException} from '@nestjs/common';
import { AppService } from './app.service';
import { JobRequestDto } from './dto/create-job-request.dto';
import { ReviewJobRequestDto } from './dto/review-job-request.dto';
import { AcceptJobRequestDto } from './dto/accept-job-request.dto';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}


  @Post()
  async create(@Body() data:JobRequestDto , @Req() req:Request){
    const userId = req.headers["x-userid"];
    const role = req.headers["x-role"];
    console.log({role,userId,h:req.headers})
    if(!userId){
      throw new UnauthorizedException("user header is missing");
    }
    if(role !== "COMPANY"){
      throw new ForbiddenException("can't access the endpoint")
    }
  
    return this.appService.create(data,userId);
  }
  
  @Post("review-job-request/:id")
  async reviewJobRequest(@Param("id")  jobRequestId:string , @Body() data:ReviewJobRequestDto, @Req() req:Request){
    const userId = req.headers["x-userid"];
    const role = req.headers["x-role"];
    console.log({role,userId,h:req.headers})
    if(role !== "ADMIN"){
      throw new ForbiddenException("can't access the aendpoint")
    }
   
    return await this.appService.reviewJobRequest(jobRequestId,data);
  }
  @Post("accept-job-request/:id")
  async accepteJobRequst(@Param("id")  jobRequestId:string,@Body() data:AcceptJobRequestDto, @Req() req:Request){
    const userId = req.headers["x-userid"];
    const role = req.headers["x-role"];
    console.log({role,userId,h:req.headers})
    if(role !== "COMPANY"){
      throw new ForbiddenException("can't access the endpoint")
    }
    if(!userId){
      throw new UnauthorizedException("user header is missing");
    }
  
    return await this.appService.accepteJobRequest(jobRequestId,data,userId)
  }

  @Get("")
  async getJobRequests(){
    return this.appService.getJobRequests();
  }
  
}
