import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, first, tap} from "rxjs";
import {AuthResponse} from "../authentication/models/auth-response";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private router: Router) {
  }

  register(user: any) {
    return this.http.post<any>("http://localhost:8083/api/auth/register", user);
  }

  login(request: any) {
    return this.http.post<AuthResponse>("http://localhost:8083/api/auth/login", request)
      .pipe(
        tap((response: AuthResponse) => {
          this.saveTokens(response.id.toString(), response.firstName, response.email, response.role, response.accessToken, response.refreshToken);
        })
      );
  }

  logout() {
    this.loggedIn.next(false);
    localStorage.removeItem('id')
    localStorage.removeItem('firstName')
    localStorage.removeItem('email')
    localStorage.removeItem('role')
    localStorage.removeItem('access_token')
    localStorage.removeItem('refresh_token')
    this.router.navigate(['login'])
  }

  public isLoggedIn() {
    return !!localStorage.getItem('access_token');
  }

  public isAdmin() {
    return localStorage.getItem('role') == 'ADMIN';
  }

  refreshToken() {
    const refreshToken = localStorage.getItem('refresh_token');
    localStorage.removeItem('access_token')
    console.log("Inside Auth service refresh token" + refreshToken);
    return this.http.post<any>(`http://localhost:8083/api/auth/refresh_token`, {}, {
      headers: {
        Authorization: `Bearer ${refreshToken}`
      }
    });
  }

  saveTokens(id: string, firstName:string, email: string, role: string, accessToken: string, refreshToken: string) {
    localStorage.setItem('id', id);
    localStorage.setItem('firstName', firstName);
    localStorage.setItem('email', email);
    localStorage.setItem('role', role);
    localStorage.setItem('access_token', accessToken);
    localStorage.setItem('refresh_token', refreshToken);
  }
}
