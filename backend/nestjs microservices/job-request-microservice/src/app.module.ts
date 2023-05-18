import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { EurekaModule } from 'nestjs-eureka';
import { MongooseModule } from '@nestjs/mongoose';
import { JobRequest, JobRequestSchema } from './schemas/job-request.schema';
import { EurekaWrapperModule } from './eureka/eurekaWrapper.module';
import { HttpModule } from '@nestjs/axios';
@Module({
  imports: [
    EurekaWrapperModule,
    HttpModule,
    MongooseModule.forRoot('mongodb://localhost:27017/job-requests-db'),
    MongooseModule.forFeature([
      { name: JobRequest.name, schema: JobRequestSchema },
    ]),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
