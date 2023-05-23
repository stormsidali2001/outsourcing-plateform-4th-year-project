export interface WorkerStatusResponse{
    status:"PENDING" | "REJECTED" |"APPROVED" | null;
    exists:boolean;
    workerId:string;
    publicPrice:number;
}