import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const jugadorGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const user = auth.currentUser(); // Signal → se obtiene así

  if (user !== null && auth.isJugador()) {
    return true;
  }

  router.navigate(['/dashboard']);
  return false;
};
