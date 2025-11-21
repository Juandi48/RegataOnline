import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const adminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.currentUser && auth.isAdmin()) {
    return true;
  }

  // Si no es admin, lo devuelvo al dashboard
  router.navigate(['/dashboard']);
  return false;
};
