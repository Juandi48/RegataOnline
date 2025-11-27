import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Barco } from '../models/barco.model';

export interface MovimientoRequest {
  barcoId: number;
  ax: number;
  ay: number;
}

@Injectable({ providedIn: 'root' })
export class GameService {

  private baseUrl = `${environment.apiV1Url}/game`;

  constructor(private http: HttpClient) {}

  listarBarcos(): Observable<Barco[]> {
    return this.http.get<Barco[]>(`${this.baseUrl}/barcos`);
  }

  mover(req: MovimientoRequest): Observable<Barco> {
    return this.http.post<Barco>(`${this.baseUrl}/mover`, req);
  }
}
