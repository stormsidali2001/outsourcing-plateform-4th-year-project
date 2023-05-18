import { Inject, Injectable } from '@nestjs/common';
import { DiscoveryService } from 'nestjs-eureka';

@Injectable()
export class LoadBalancerService {
  constructor(@Inject(DiscoveryService) private eureka: DiscoveryService) {}

  getInstanceId(serviceName: string) {
    //@ts-ignore
    const instances = this.eureka.client
    .getInstancesByAppId(serviceName);
    console.log("eureka" +JSON.stringify(instances))
    const instanceIds = instances
      .map((el) => el.ipAddr +":"+el.port.$);
      console.log("eureka" +JSON.stringify(instanceIds))
    return instanceIds[Math.floor(Math.random() * instanceIds.length)];
  }
}
