export type TipoCelda = 'AGUA' | 'PARED' | 'PARTIDA' | 'META';

export interface Celda {
  x: number;
  y: number;
  tipo: TipoCelda;
}
