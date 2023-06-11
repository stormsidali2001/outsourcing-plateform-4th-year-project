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
  
  // @Post("review-job-request/:id")
  // async reviewJobRequest(@Param("id")  jobRequestId:string , @Body() data:ReviewJobRequestDto, @Req() req:Request){
  //   const userId = req.headers["x-userid"];
  //   const role = req.headers["x-role"];
  //   console.log({role,userId,h:req.headers})
  //   if(role !== "ADMIN"){
  //     throw new ForbiddenException("can't access the aendpoint")
  //   }
   
  //   return await this.appService.reviewJobRequest(jobRequestId,data);
  // }
  // @Post("accept-job-request/:id")
  // async accepteJobRequst(@Param("id")  jobRequestId:string,@Body() data:AcceptJobRequestDto, @Req() req:Request){
  //   const userId = req.headers["x-userid"];
  //   const role = req.headers["x-role"];
  //   console.log({role,userId,h:req.headers})
  //   if(role !== "COMPANY"){
  //     throw new ForbiddenException("can't access the endpoint")
  //   }
  //   if(!userId){
  //     throw new UnauthorizedException("user header is missing");
  //   }
  
  //   return await this.appService.accepteJobRequest(jobRequestId,data,userId)
  // }

    @Post(":id/accept")
  async accepteJobRequstAdmin(@Param("id")  jobRequestId:string,@Body() data:AcceptJobRequestDto, @Req() req:Request){
    const userId = req.headers["x-userid"];
    const role = req.headers["x-role"];
    console.log({role,userId,h:req.headers})
    if(role !== "ADMIN"){
      throw new ForbiddenException("can't access the endpoint")
    }
    if(!userId){
      throw new UnauthorizedException("user header is missing");
    }
    return this.appService.accepteJobRequstAdmin(jobRequestId,data)
  
  }


  @Get("all")
  async getJobRequests(){
    return this.appService.getJobRequests();
  }
  @Get("all/:id")
  async getJobRequestDetails(@Param("id") jobRequestId:string){
    return this.appService.getJobRequestDetails(jobRequestId);
  }
  
  @Get("company")
  async getCompanyJobRequests( @Req() req:Request){
    const userId = req.headers["x-userid"];
    const role = req.headers["x-role"];
    console.log({role,userId,h:req.headers})
    if(role !== "COMPANY"){
      throw new ForbiddenException("can't access the endpoint")
    }
    if(!userId){
      throw new UnauthorizedException("user header is missing");
    }
    return this.appService.getCompanyJobRequests(userId)
    
  }

  @Get("contracts")
  async getAllContracts(){
    return this.appService.getAllContracts()
  }
  
}
