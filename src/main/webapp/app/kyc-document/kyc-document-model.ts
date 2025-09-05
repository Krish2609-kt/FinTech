export class KycDocumentDTO {

  constructor(data:Partial<KycDocumentDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  docType?: string|null;
  docNumber?: string|null;
  docFilePath?: string|null;
  verified?: boolean|null;
  uploadedAt?: string|null;
  user?: number|null;

}
