import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { GameService, MovimientoRequest } from '../../core/services/game.service';
import { Barco } from '../../core/models/barco.model';

@Component({
  selector: 'app-juego',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf],
  templateUrl: './juego.component.html',
  styleUrls: ['./juego.component.css']
})
export class JuegoComponent implements OnInit {

  barcos: Barco[] = [];
  selectedBarcoId?: number;

  deltaVx: number = 0;
  deltaVy: number = 0;

  mensaje?: string;

  constructor(private gameSrv: GameService) {}

  ngOnInit(): void {
    this.cargarBarcos();
  }

  cargarBarcos(): void {
    this.gameSrv.listarBarcos().subscribe(bs => {
      this.barcos = bs;
      if (!this.selectedBarcoId && this.barcos.length > 0) {
        this.selectedBarcoId = this.barcos[0].id;
      }
    });
  }

  aplicarMovimiento(): void {
    if (!this.selectedBarcoId) {
      this.mensaje = 'Debe seleccionar un barco.';
      return;
    }

    const req: MovimientoRequest = {
      barcoId: this.selectedBarcoId,
      deltaVx: this.deltaVx,
      deltaVy: this.deltaVy
    };

    this.gameSrv.mover(req).subscribe({
      next: (barcoActualizado) => {
        // Actualizamos el array local
        const idx = this.barcos.findIndex(b => b.id === barcoActualizado.id);
        if (idx >= 0) {
          this.barcos[idx] = barcoActualizado;
        }
        this.mensaje = `Barco ${barcoActualizado.id} movido a (${barcoActualizado.posX}, ${barcoActualizado.posY}) con velocidad (${barcoActualizado.velX}, ${barcoActualizado.velY}).`;
      },
      error: (err) => {
        console.error(err);
        this.mensaje = 'Error al aplicar movimiento.';
      }
    });
  }
}
