import {Component, OnInit} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms'
import {AuthService} from "../../services/auth.service";
import {User} from "../../user";
import {LoginRequest} from "../../login-request";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
  hide: boolean = true;
  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
  }

  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&.,])[A-Za-z\d@$!%*?&.,]{8,}$/)]]
  })
  onLogin() {
    if (!this.loginForm.valid) {
      return;
    }

    let creds = new LoginRequest(this.loginForm.controls['email'].value,
      this.loginForm.controls['password'].value);

    let response =  this.authService.login(creds);
    response.subscribe(data=> {
      console.log(data)
      this.router.navigate(['/payment'])
    })
  }

}
