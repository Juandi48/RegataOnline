// src/app/core/services/barco.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Barco } from '../models/barco.model';

@Injectable({ providedIn: 'root' })
export class BarcoService {

  // Usamos la URL de la API v1
  private baseUrl = `${environment.apiV1Url}/barcos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Barco[]> {
    return this.http.get<Barco[]>(this.baseUrl);
  }

  obtener(id: number): Observable<Barco> {
    return this.http.get<Barco>(`${this.baseUrl}/${id}`);
  }

  // Para crear barco desde BarcosComponent
  crear(payload: {
    jugadorId: number;
    modeloId: number;
    velX: number;
    velY: number;
    posX: number;
    posY: number;
  }): Observable<Barco> {
    return this.http.post<Barco>(this.baseUrl, payload);
  }

  // ✅ Método que te faltaba y que usa JuegoComponent
  actualizar(id: number, barco: Partial<Barco>): Observable<Barco> {
    return this.http.put<Barco>(`${this.baseUrl}/${id}`, barco);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
