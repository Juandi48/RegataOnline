import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { BarcoService } from '../../core/services/barco.service';
import { AuthService } from '../../core/services/auth.service';
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
  barco?: Barco;

  constructor(
    private barcoService: BarcoService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.barcoService.listar().subscribe(bs => {

      // Solo barcos del jugador
      if (this.auth.isJugador()) {
        this.barcos = bs.filter(b => b.jugadorId === this.auth.currentUser?.id);
      } else {
        // Admin ve todos
        this.barcos = bs;
      }
    });
  }

  seleccionarBarco(): void {
    this.barco = this.barcos.find(b => b.id === this.selectedBarcoId);
  }

  mover(dx: number, dy: number): void {
    if (!this.barco) return;

    // actualizar la velocidad según dx/dy
    this.barco.velX += dx;
    this.barco.velY += dy;

    // mover al barco
    this.barco.posX += this.barco.velX;
    this.barco.posY += this.barco.velY;

    // enviar al backend
    this.barcoService.actualizar(this.barco.id!, this.barco)
      .subscribe();
  }
}
