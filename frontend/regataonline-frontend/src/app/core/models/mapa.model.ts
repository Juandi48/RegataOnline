import { Celda } from './celda.model';

export interface Mapa {
  id?: number;
  nombre: string;
  ancho: number;
  alto: number;
  celdas: Celda[];
}
