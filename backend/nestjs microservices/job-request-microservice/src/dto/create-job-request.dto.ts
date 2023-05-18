import { ApiProperty } from "@nestjs/swagger";
import { IsUUID,IsArray } from "class-validator";

export class JobRequestDto{
    @ApiProperty({example:"companyId"})
    @IsUUID()
    companyId:string;

    @ApiProperty({example:["id1","id2"],type:Array})
    @IsArray()
    @IsUUID(4,{each:true})
    workerIds:string[];
}