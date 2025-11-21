// src/app/core/services/jugador.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Jugador } from '../models/jugador.model';

@Injectable({ providedIn: 'root' })
export class JugadorService {

  // Ahora usa apiV1Url → http://localhost:8081/api/v1/jugadores
  private baseUrl = `${environment.apiV1Url}/jugadores`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Jugador[]> {
    return this.http.get<Jugador[]>(this.baseUrl);
  }

  obtener(id: number): Observable<Jugador> {
    return this.http.get<Jugador>(`${this.baseUrl}/${id}`);
  }

  crear(j: Partial<Jugador>): Observable<Jugador> {
    return this.http.post<Jugador>(this.baseUrl, j);
  }

  actualizar(id: number, j: Partial<Jugador>): Observable<Jugador> {
    return this.http.put<Jugador>(`${this.baseUrl}/${id}`, j);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
