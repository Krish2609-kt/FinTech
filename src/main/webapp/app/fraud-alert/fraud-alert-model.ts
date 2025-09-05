export class FraudAlertDTO {

  constructor(data:Partial<FraudAlertDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  alertType?: string|null;
  details?: string|null;
  status?: string|null;
  createdAt?: string|null;
  transaction?: number|null;

}
