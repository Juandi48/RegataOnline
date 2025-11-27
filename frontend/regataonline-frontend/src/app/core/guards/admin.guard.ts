import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const adminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const user = auth.currentUser(); // Signal → se lee como función

  if (user && auth.isAdmin()) {
    return true;
  }

  router.navigate(['/dashboard']);
  return false;
};
