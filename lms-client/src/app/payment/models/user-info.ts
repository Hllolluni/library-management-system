export class UserInfo {
  userId: number;
  firstName: string;
  email: string;

  constructor(userId: number, firstName: string, email: string) {
    this.userId = userId;
    this.firstName = firstName;
    this.email = email;
  }
}
