import { Mapa } from './mapa.model';
import { Barco } from './barco.model';
import { Jugador } from './jugador.model';

export type EstadoCarrera = 'EN_CURSO' | 'GANADO' | 'DESTRUIDO';

export interface EstadoJuego {
  turno: number;
  estado: EstadoCarrera;
  mapa: Mapa;
  barco: Barco;
  jugador?: Jugador;
  mensaje?: string;
}

export interface MovimientoRequest {
  deltaVX: number;
  deltaVY: number;
}
