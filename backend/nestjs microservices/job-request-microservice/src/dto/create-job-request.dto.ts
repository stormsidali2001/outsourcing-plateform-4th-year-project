import { ApiProperty } from "@nestjs/swagger";
import { IsUUID,IsArray, IsEnum, IsNumber, Min, IsDateString, ValidateNested } from "class-validator";
import { HiringType } from "src/types/hiring-type";
class WorkerDto{
    @ApiProperty({example:"id"})
    @IsUUID(4)
    workerId: string;

    @ApiProperty()
    @IsDateString()
    startDate: string;

    @ApiProperty()
    @IsDateString()
    endDate: string;

    @ApiProperty({type:"enum",enum:HiringType})
    @IsEnum(HiringType)
    hiringType: HiringType;



}
export class JobRequestDto{
    @ApiProperty({example:"companyId"})
    @IsUUID()
    companyId:string;

    
    @ApiProperty({type:Array,example:[
        {
            workerId:"id",
            startDate:(new Date).toISOString(),
            endDate:(new Date).toISOString(),
            hiringType:HiringType.PARTTIME,

        }
    ]})
    @IsArray()
    @ValidateNested()
    workers:WorkerDto[];
}