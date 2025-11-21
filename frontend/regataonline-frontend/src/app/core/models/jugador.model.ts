export interface Jugador {
  id?: number;
  nombre: string;
  email: string;
  rol?: 'ADMIN' | 'JUGADOR';
  password?: string;  // solo se usa para crear/editar desde admin
}
