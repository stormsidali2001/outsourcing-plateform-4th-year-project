import { ApiProperty } from "@nestjs/swagger";
import { IsBoolean, IsUUID } from "class-validator";

export class AcceptJobRequestDto{
   
    @ApiProperty({example:true,type:Boolean})
    @IsBoolean()
    accepted:boolean;

    @ApiProperty({example:true,type:Boolean})
    jobRequestId:string;
}