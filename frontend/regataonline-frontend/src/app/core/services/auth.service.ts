import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Jugador } from '../models/jugador.model';

export interface AuthUser {
  id: number;
  nombre: string;
  email: string;
  rol: 'ADMIN' | 'JUGADOR';
  token: string;          // Basic token
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly TOKEN_KEY = 'authToken';
  private readonly USER_KEY  = 'currentUser';

  private apiV1 = environment.apiV1Url;

  constructor(private http: HttpClient) {
    // Restaurar usuario si ya estaba guardado
    const stored = localStorage.getItem(this.USER_KEY);
    if (stored) {
      this._currentUser = JSON.parse(stored);
    }
  }

  private _currentUser: AuthUser | null = null;

  /** Usuario autenticado actual */
  get currentUser(): AuthUser | null {
    return this._currentUser;
  }

  /** Rol actual (o null si no hay login) */
  get role(): 'ADMIN' | 'JUGADOR' | null {
    return this._currentUser?.rol ?? null;
  }

  /** Helpers de rol */
  isAdmin(): boolean {
    return this.role === 'ADMIN';
  }

  isJugador(): boolean {
    return this.role === 'JUGADOR';
  }

  /** Token Basic actual */
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  /** Login con email y password */
  login(email: string, password: string): Observable<Jugador> {
    const basic = 'Basic ' + btoa(`${email}:${password}`);

    const headers = new HttpHeaders({
      Authorization: basic
    });

    // /auth/me devuelve el Jugador logueado (id, nombre, email, rol)
    return this.http.get<Jugador>(`${this.apiV1}/auth/me`, { headers }).pipe(
      tap(j => {
        const authUser: AuthUser = {
          id: j.id!,
          nombre: j.nombre,
          email: j.email,
          rol: j.rol as 'ADMIN' | 'JUGADOR',
          token: basic
        };

        this._currentUser = authUser;
        localStorage.setItem(this.TOKEN_KEY, basic);
        localStorage.setItem(this.USER_KEY, JSON.stringify(authUser));
      })
    );
  }

  /** Logout */
  logout(): void {
    this._currentUser = null;
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
  }
}
