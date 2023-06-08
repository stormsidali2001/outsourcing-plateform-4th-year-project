import { ApiProperty } from "@nestjs/swagger";
import { IsUUID } from "class-validator";

export class AcceptJobRequestDto{
   
    @ApiProperty({example:true,type:Boolean})
    accepted:boolean;
}