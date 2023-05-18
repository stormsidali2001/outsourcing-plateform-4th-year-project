import { Body, Controller, Get , Post} from '@nestjs/common';
import { AppService } from './app.service';
import { JobRequestDto } from './dto/create-job-request.dto';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}


  @Post()
  async create(@Body() data:JobRequestDto){
    return this.appService.create(data);
  }
}
