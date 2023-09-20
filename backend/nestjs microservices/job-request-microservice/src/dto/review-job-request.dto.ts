import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsOptional, IsUUID } from "class-validator";

export class ReviewJobRequestDto{
   

    @ApiProperty({example:["id","id"]})
    removedWorkerIds:string[];

    @IsOptional()
    @ApiPropertyOptional({example:"observation"})
    observation:string;


}