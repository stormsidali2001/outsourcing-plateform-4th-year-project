
import { DynamicModule, Global, Module, Logger } from '@nestjs/common';
import { Eureka, EurekaClient } from 'eureka-js-client';
export const EUREKA_CLIENT_KEY = 'EUREKA_CLIENT';
@Global()
@Module({})
export class EurekaModule  {
  static forRoot(options: Eureka.EurekaConfig): DynamicModule {
    const eurekaProvider = {
      provide: EUREKA_CLIENT_KEY,
      useFactory: () => {
        const logger = new Logger('EurekaClient');

        const client:Eureka = new Eureka(options);

        return client;
      },
    };

    return {
      module: EurekaModule,
      providers: [eurekaProvider],
      exports: [eurekaProvider],
    };
  }
}