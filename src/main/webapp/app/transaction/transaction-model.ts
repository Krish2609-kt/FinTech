export class TransactionDTO {

  constructor(data:Partial<TransactionDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  amount?: string|null;
  currency?: string|null;
  status?: string|null;
  transactionType?: string|null;
  initiatedAt?: string|null;
  completedAt?: string|null;
  fromAccount?: number|null;
  toAccount?: number|null;

}
