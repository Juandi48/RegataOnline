// src/app/core/services/auth.service.ts
import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable, map, tap } from 'rxjs';

export interface AuthUser {
  id: number;
  nombre: string;
  email: string;
  rol: 'ADMIN' | 'JUGADOR';
}

interface LoginResponse {
  token: string;
  id: number;
  nombre: string;
  email: string;
  rol: 'ADMIN' | 'JUGADOR';
}

const TOKEN_KEY = 'auth_token';
const USER_KEY  = 'auth_user';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private currentUserSignal = signal<AuthUser | null>(null);

  constructor(private http: HttpClient) {
    // Restaurar sesión si ya había usuario guardado
    const userJson = localStorage.getItem(USER_KEY);
    if (userJson) {
      try {
        const user: AuthUser = JSON.parse(userJson);
        this.currentUserSignal.set(user);
      } catch {
        localStorage.removeItem(USER_KEY);
      }
    }
  }

  // ========= helpers públicos =========

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  currentUser(): AuthUser | null {
    return this.currentUserSignal();
  }

  isAdmin(): boolean {
    return this.currentUserSignal()?.rol === 'ADMIN';
  }

  isJugador(): boolean {
    return this.currentUserSignal()?.rol === 'JUGADOR';
  }

  // ========= LOGIN SOLO USA /auth/login =========

  login(email: string, password: string): Observable<AuthUser> {
    const body = { email, password };

    return this.http.post<LoginResponse>(`${environment.apiV1Url}/auth/login`, body).pipe(
      tap(resp => {
        if (!resp || !resp.token) {
          throw new Error('Login sin token en la respuesta');
        }

        // Guardar token en localStorage (para el interceptor)
        localStorage.setItem(TOKEN_KEY, resp.token);

        // Construir usuario y guardarlo
        const user: AuthUser = {
          id: resp.id,
          nombre: resp.nombre,
          email: resp.email,
          rol: resp.rol
        };

        localStorage.setItem(USER_KEY, JSON.stringify(user));
        this.currentUserSignal.set(user);
      }),
      // El observable que ve el componente solo emite el usuario
      map(resp => ({
        id: resp.id,
        nombre: resp.nombre,
        email: resp.email,
        rol: resp.rol
      }))
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.currentUserSignal.set(null);
  }
}
