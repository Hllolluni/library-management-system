export class AuthResponse {
  id: number;
  firstName: string;
  email: string;
  role: string;
  accessToken!: string;
  refreshToken! : string;


  constructor(id: number, firstName: string, email: string, role: string, accessToken: string, refreshToken: string) {
    this.id = id;
    this.firstName = firstName;
    this.email = email;
    this.role = role;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
