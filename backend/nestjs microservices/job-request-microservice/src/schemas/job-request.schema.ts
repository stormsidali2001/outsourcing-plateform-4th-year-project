import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { HydratedDocument } from 'mongoose';
import { HiringType } from 'src/types/hiring-type';
export type JobRequestDocument = HydratedDocument<JobRequest>;

class Worker{
    @Prop()
    workerId: string;

    @Prop()
    startDate: Date;

    @Prop()
    endDate: Date;

    @Prop({enum:HiringType})
    hringType: HiringType;

    @Prop()
    contractUrl:string;
}

@Schema()
export class JobRequest{
    @Prop()
    companyId: string;

    @Prop([Worker])
    workers:Worker[];

}


export const JobRequestSchema = SchemaFactory.createForClass(JobRequest);