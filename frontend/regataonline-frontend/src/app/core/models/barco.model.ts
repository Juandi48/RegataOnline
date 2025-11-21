export interface Barco {
  id?: number;

  // claves foráneas
  modeloId?: number;
  jugadorId?: number;

  // estado físico
  posX: number;
  posY: number;
  velX: number;
  velY: number;

  // SOLO PARA VISTAS (no son obligatorios)
  nombreModelo?: string;
  nombreJugador?: string;
}
