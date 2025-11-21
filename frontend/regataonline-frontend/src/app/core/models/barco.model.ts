import { Jugador } from './jugador.model';
import { ModeloBarco } from './modelo-barco.model';

export interface Barco {
  id?: number;

  // IDs que usamos al crear barcos desde Angular
  jugadorId?: number;
  modeloId?: number;

  // Objetos completos que vienen del backend
  jugador?: Jugador;
  modelo?: ModeloBarco;

  // Estado en el mapa
  posX: number;
  posY: number;
  velX: number;
  velY: number;
}
