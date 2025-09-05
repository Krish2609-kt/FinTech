export class AccountDTO {

  constructor(data:Partial<AccountDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  accountNumber?: string|null;
  accountType?: string|null;
  balance?: string|null;
  currency?: string|null;
  createdAt?: string|null;
  user?: number|null;

}
