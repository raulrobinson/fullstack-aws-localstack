import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  // Simulación: verifica si el usuario está autenticado (usa localStorage o un AuthService real)
  const isAuthenticated = localStorage.getItem('auth') === 'true';

  if (!isAuthenticated) {
    router.navigate(['/login']);
    return false;
  }

  return true;
};
