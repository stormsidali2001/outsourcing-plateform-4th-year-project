export interface JobRequestPayementResponse{
    idBill:       number;
    idCompany:    string;
    jobRequestId: string;
    billAmount:   number;
    createdAt:    Date;
    transactions: any[];
}