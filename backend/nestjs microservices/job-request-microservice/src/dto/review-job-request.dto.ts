import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsOptional, IsUUID } from "class-validator";

export class ReviewJobRequestDto{
   

    @ApiProperty({example:["id","id"]})
    @IsUUID(4,{each:true})
    removedWorkerIds:string[];

    @IsOptional()
    @ApiPropertyOptional({example:"observation"})
    observation:string;


}