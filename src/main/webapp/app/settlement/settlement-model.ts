export class SettlementDTO {

  constructor(data:Partial<SettlementDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  date?: string|null;
  totalTransactions?: number|null;
  totalAmount?: string|null;
  status?: string|null;
  generatedAt?: string|null;

}
