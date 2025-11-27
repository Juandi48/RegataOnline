// src/app/core/services/barco.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Barco } from '../models/barco.model';

@Injectable({ providedIn: 'root' })
export class BarcoService {

  // Base de la API REST de barcos
  private baseUrl = `${environment.apiV1Url}/barcos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos los barcos.
   * El backend devuelve algo como:
   * {
   *   id, posX, posY, velX, velY,
   *   modelo: { id, nombre, colorHex },
   *   jugador: { id, nombre, email }
   * }
   *
   * Aquí lo "aplanamos" al modelo Barco del front.
   */
  listar(): Observable<Barco[]> {
    return this.http.get<any[]>(this.baseUrl).pipe(
      map(response =>
        response.map(b => ({
          id: b.id,
          jugadorId: b.jugador?.id,
          modeloId: b.modelo?.id,
          nombreJugador: b.jugador?.nombre,
          nombreModelo: b.modelo?.nombre,
          posX: b.posX,
          posY: b.posY,
          velX: b.velX,
          velY: b.velY
        } as Barco))
      )
    );
  }

  /**
   * Obtener un barco concreto
   */
  obtener(id: number): Observable<Barco> {
    return this.http.get<any>(`${this.baseUrl}/${id}`).pipe(
      map(b => ({
        id: b.id,
        jugadorId: b.jugador?.id,
        modeloId: b.modelo?.id,
        nombreJugador: b.jugador?.nombre,
        nombreModelo: b.modelo?.nombre,
        posX: b.posX,
        posY: b.posY,
        velX: b.velX,
        velY: b.velY
      } as Barco))
    );
  }

  /**
   * Crear barco (usa el DTO que espera el backend: BarcoCreateDTO)
   */
  crear(payload: {
    jugadorId: number;
    modeloId: number;
    velX: number;
    velY: number;
    posX: number;
    posY: number;
  }): Observable<Barco> {
    return this.http.post<any>(this.baseUrl, payload).pipe(
      map(b => ({
        id: b.id,
        jugadorId: b.jugador?.id,
        modeloId: b.modelo?.id,
        nombreJugador: b.jugador?.nombre,
        nombreModelo: b.modelo?.nombre,
        posX: b.posX,
        posY: b.posY,
        velX: b.velX,
        velY: b.velY
      } as Barco))
    );
  }

  /**
   * Eliminar barco
   */
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  /**
   * (Opcional) actualizar barco.
   * OJO: ahora el movimiento se hace por /api/v1/game/mover,
   * así que probablemente no uses este método.
   */
  actualizar(id: number, barco: Partial<Barco>): Observable<Barco> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, barco).pipe(
      map(b => ({
        id: b.id,
        jugadorId: b.jugador?.id,
        modeloId: b.modelo?.id,
        nombreJugador: b.jugador?.nombre,
        nombreModelo: b.modelo?.nombre,
        posX: b.posX,
        posY: b.posY,
        velX: b.velX,
        velY: b.velY
      } as Barco))
    );
  }
}
