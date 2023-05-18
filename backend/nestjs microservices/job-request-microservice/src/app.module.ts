import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { EurekaModule } from 'nestjs-eureka';
import { MongooseModule } from '@nestjs/mongoose';
import { JobRequest, JobRequestSchema } from './schemas/job-request.schema';
@Module({
  imports: [
    EurekaModule.forRoot({
      eureka: {
        host: 'localhost',
      port: 8888,
      registryFetchInterval: 1000,
      servicePath: '/eureka/apps/',
      maxRetries: 3,
      },
      service: {
        name: 'job-request-service',
        port: 5006,
      },
    }),

    MongooseModule.forRoot("mongodb://localhost:27017/job-requests-db"),
    MongooseModule.forFeature([{name:JobRequest.name,schema:JobRequestSchema}])
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
