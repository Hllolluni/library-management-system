export class User {
  firstName: String;
  lastName: String;
  email: String;
  password: String;
  phoneNumber: String;

  constructor(firstName: String, lastName: String, email: String, password: String, phoneNumber: String) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
  }
}
