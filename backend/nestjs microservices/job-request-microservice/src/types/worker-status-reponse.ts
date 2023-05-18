export interface WorkerStatusResponse{
    status:"PENDING" | "REJECTED" |"ACTIVE" | null;
    exists:boolean;
    workerId:string;
}