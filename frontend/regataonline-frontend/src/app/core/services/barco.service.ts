import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Barco } from '../models/barco.model';

export interface BarcoCreateRequest {
  jugadorId: number;
  modeloId: number;
  posX: number;
  posY: number;
  velX: number;
  velY: number;
}

@Injectable({ providedIn: 'root' })
export class BarcoService {

  // OJO: apiV1Url -> http://localhost:8081/api/v1
  private baseUrl = `${environment.apiV1Url}/barcos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Barco[]> {
    return this.http.get<Barco[]>(this.baseUrl);
  }

  obtener(id: number): Observable<Barco> {
    return this.http.get<Barco>(`${this.baseUrl}/${id}`);
  }

  crear(req: BarcoCreateRequest): Observable<Barco> {
    return this.http.post<Barco>(this.baseUrl, req);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
