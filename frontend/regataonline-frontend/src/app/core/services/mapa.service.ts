import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Mapa } from '../models/mapa.model';

@Injectable({ providedIn: 'root' })
export class MapaService {

  private baseUrl = `${environment.apiUrl}/mapas`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Mapa[]> {
    return this.http.get<Mapa[]>(this.baseUrl);
  }

  obtener(id: number): Observable<Mapa> {
    return this.http.get<Mapa>(`${this.baseUrl}/${id}`);
  }

  // para tu editor, normalmente mandarías también las celdas
  crear(mapa: Mapa): Observable<Mapa> {
    return this.http.post<Mapa>(this.baseUrl, mapa);
  }
}
