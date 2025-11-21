import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const jugadorGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.currentUser && auth.isJugador()) {
    return true;
  }

  // Si no es jugador, lo mando al dashboard
  router.navigate(['/dashboard']);
  return false;
};
