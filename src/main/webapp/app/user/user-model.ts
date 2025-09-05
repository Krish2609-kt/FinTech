export class UserDTO {

  constructor(data:Partial<UserDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  username?: string|null;
  passwordHash?: string|null;
  email?: string|null;
  phone?: string|null;
  fullName?: string|null;
  kycStatus?: string|null;
  createdAt?: string|null;
  updatedAt?: string|null;
  userRoleRoles?: number[]|null;

}
