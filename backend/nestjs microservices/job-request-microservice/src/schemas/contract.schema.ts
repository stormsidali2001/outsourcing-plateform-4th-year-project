import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose";
import { ContractStatus } from "src/types/contract-status.enum";
import { HydratedDocument } from 'mongoose';

export type ContractDocument = HydratedDocument<Contract>;

@Schema()
export class Contract{
    @Prop({required:true})
    workerId:string;

    @Prop({required:true})
    companyId:string;

    @Prop({enum:ContractStatus,default:ContractStatus.ACTIVE})
    status:ContractStatus;

    @Prop({required:true})
    startDate:Date;

    @Prop({required:true})
    endDate:Date;
}

export const ContractSchema = SchemaFactory.createForClass(Contract)