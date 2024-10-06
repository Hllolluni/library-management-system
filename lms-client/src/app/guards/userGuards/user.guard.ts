import { CanActivateFn } from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth.service";

export const userGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  if (authService.isAdmin()) {
    return false;
  }
  return true;
};
