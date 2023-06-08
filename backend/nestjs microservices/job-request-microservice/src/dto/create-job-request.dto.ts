import { ApiProperty } from "@nestjs/swagger";
import { IsUUID,IsArray, IsEnum, IsNumber, Min, IsDateString, ValidateNested } from "class-validator";
class WorkerDto{
    @ApiProperty({example:"id"})
    @IsUUID(4)
    workerId: string;

    @ApiProperty()
    @IsNumber()
    nbHours: number;

}
export class JobRequestDto{
   

    
    @ApiProperty({type:Array,example:[
        {
            workerId:"id",
            nbHours:5

        }
    ]})
    @IsArray()
    @ValidateNested()
    workers:WorkerDto[];
}