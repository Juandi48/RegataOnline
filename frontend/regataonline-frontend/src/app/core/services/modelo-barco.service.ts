import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ModeloBarco } from '../models/modelo-barco.model';

@Injectable({
  providedIn: 'root'
})
export class ModeloBarcoService {

  private api = `${environment.apiV1Url}/modelos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<ModeloBarco[]> {
    return this.http.get<ModeloBarco[]>(this.api);
  }

  crear(data: Partial<ModeloBarco>): Observable<ModeloBarco> {
    return this.http.post<ModeloBarco>(this.api, data);
  }

  actualizar(id: number, data: Partial<ModeloBarco>): Observable<ModeloBarco> {
    return this.http.put<ModeloBarco>(`${this.api}/${id}`, data);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
