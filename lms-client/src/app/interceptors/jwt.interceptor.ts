import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, switchMap, throwError} from 'rxjs';
import {AuthService} from "../services/auth.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let accessToken = localStorage.getItem("access_token");

    if (accessToken) {
      request = this.addToken(request, accessToken);
    }

    return next.handle(request).pipe(
      catchError(error => {
        if (error.status == 401) {
          console.log("Inside interceptor!")
          return this.authService.refreshToken().pipe(
            switchMap((token: any) => {
              console.log("Inside interceptor" + token)
              localStorage.setItem('id', token.id);
              localStorage.setItem('firstName', token.firstName);
              localStorage.setItem('email', token.email);
              localStorage.setItem('role', token.role);
              localStorage.setItem('access_token', token.accessToken);
              localStorage.setItem('refresh_token', token.refreshToken)
              let authRequest = this.addToken(request, token.accessToken)
              return next.handle(authRequest);
            }),
            catchError(err => {
              this.authService.logout();
              return throwError(err);
            })
          )
        }
        return throwError(error)
      })
    );
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }


}
