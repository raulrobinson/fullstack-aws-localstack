import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from "@shared/services/auth.service";
import {catchError, map, of} from "rxjs";

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  return authService.hasValidToken().pipe(
    map((valid) => {
      if (!valid) {
        router.navigate(['/login']);
        return false;
      }
      return true;
    }),
    catchError((err) => {
      router.navigate(['/login']);
      return of(false);
    })
  );
};
