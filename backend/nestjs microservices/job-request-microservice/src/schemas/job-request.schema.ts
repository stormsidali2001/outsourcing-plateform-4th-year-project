import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { HydratedDocument } from 'mongoose';
type JobRequestDocument = HydratedDocument<JobRequest>;
@Schema()
export class JobRequest{
    @Prop()
    companyId: string;

    @Prop([String])
    workerIds:string[];

}

export const JobRequestSchema = SchemaFactory.createForClass(JobRequest);