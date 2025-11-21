import { Routes } from '@angular/router';

import { LoginComponent } from './features/login/login.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { JugadoresComponent } from './features/jugadores/jugadores.component';
import { ModelosComponent } from './features/modelos/modelos.component';
import { BarcosComponent } from './features/barcos/barcos.component';
import { MapasComponent } from './features/mapas/mapas.component';
import { JuegoComponent } from './features/juego/juego.component';

import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';
import { jugadorGuard } from './core/guards/jugador.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  // Cualquier usuario autenticado puede ver el dashboard
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },

  // CRUD solo ADMIN
  { path: 'jugadores', component: JugadoresComponent, canActivate: [authGuard, adminGuard] },
  { path: 'modelos',   component: ModelosComponent,   canActivate: [authGuard, adminGuard] },
  { path: 'barcos',    component: BarcosComponent,    canActivate: [authGuard, adminGuard] },
  { path: 'mapas',     component: MapasComponent,     canActivate: [authGuard, adminGuard] },

  // Juego solo JUGADOR
  { path: 'juego',     component: JuegoComponent,     canActivate: [authGuard, jugadorGuard] },
];
