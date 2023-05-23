import { ApiProperty } from "@nestjs/swagger";
import { IsUUID } from "class-validator";

export class AcceptJobRequestDto{
    @ApiProperty({example:"id"})
    @IsUUID(4,{})
    companyId:string;

    @ApiProperty({example:true,type:Boolean})
    accepted:boolean;
}