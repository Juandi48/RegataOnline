import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ModeloBarco } from '../models/modelo-barco.model';

@Injectable({ providedIn: 'root' })
export class ModeloBarcoService {

  // También en /api
  private baseUrl = `${environment.apiUrl}/modelos-barcos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<ModeloBarco[]> {
    return this.http.get<ModeloBarco[]>(this.baseUrl);
  }

  obtener(id: number): Observable<ModeloBarco> {
    return this.http.get<ModeloBarco>(`${this.baseUrl}/${id}`);
  }

  crear(m: Partial<ModeloBarco>): Observable<ModeloBarco> {
    return this.http.post<ModeloBarco>(this.baseUrl, m);
  }

  actualizar(id: number, m: Partial<ModeloBarco>): Observable<ModeloBarco> {
    return this.http.put<ModeloBarco>(`${this.baseUrl}/${id}`, m);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
