
import { DynamicModule, Global, Module, Logger } from '@nestjs/common';
import { LoadBalancerService } from './LoadBalancer.service';
import { EurekaModule } from 'nestjs-eureka';

@Global()
@Module({
  imports:[ EurekaModule.forRoot({
    eureka: {
      host: 'localhost',
      port: 8888,
      registryFetchInterval: 1000,
      servicePath: '/eureka/apps/',
      maxRetries: 10,
    },
    service: {
      name: 'job-request-service',
      port: 5006,
    },
  }),
],
  providers:[LoadBalancerService],
  exports:[LoadBalancerService],
})
export class EurekaWrapperModule  {

}