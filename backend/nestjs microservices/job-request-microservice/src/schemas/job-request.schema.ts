import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { HydratedDocument } from 'mongoose';
import { HiringType } from 'src/types/hiring-type';
import { JobRequestStatus } from 'src/types/job-request-status.enum';
export type JobRequestDocument = HydratedDocument<JobRequest>;

class Worker{
    @Prop({required:true})
    workerId: string;

    @Prop({required:true})
    nbHours:number;

    @Prop({required:true})
    publicPrice: number;

    @Prop()
    removed?:Date;
}

@Schema()
export class JobRequest{
    @Prop({required:true})
    companyId: string;

    @Prop([Worker])
    workers:Worker[];

    @Prop({enum:JobRequestStatus,default:JobRequestStatus.PENDING})
    status:JobRequestStatus;

    @Prop()
    observation?:string;

}


export const JobRequestSchema = SchemaFactory.createForClass(JobRequest);