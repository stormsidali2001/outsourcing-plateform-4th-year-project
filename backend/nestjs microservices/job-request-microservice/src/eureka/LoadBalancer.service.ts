import { Inject, Injectable } from '@nestjs/common';
import { DiscoveryService } from 'nestjs-eureka';

@Injectable()
export class LoadBalancerService {
  constructor(@Inject(DiscoveryService) private eureka: DiscoveryService) {}

  getInstanceIds(serviceName: string) {
    //@ts-ignore
    const instanceIds = this.eureka.client
      .getInstancesByAppId(serviceName)
      .map((el) => el.instanceId);
    return instanceIds;
  }
}
