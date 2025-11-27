import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { BarcoService } from '../../core/services/barco.service';
import { AuthService } from '../../core/services/auth.service';
import { Barco } from '../../core/models/barco.model';
import { GameService, MovimientoRequest } from '../../core/services/game.service';

@Component({
  selector: 'app-juego',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './juego.component.html',
  styleUrls: ['./juego.component.css']
})
export class JuegoComponent implements OnInit {

  barcos = signal<Barco[]>([]);
  selectedBarcoId = signal<number | null>(null);
  barco = signal<Barco | null>(null);
  errorMsg = signal<string | null>(null);

  constructor(
    private barcoService: BarcoService,
    private gameService: GameService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.errorMsg.set(null);

    this.barcoService.listar().subscribe({
      next: bs => {
        const current = this.auth.currentUser();
        if (this.auth.isJugador() && current) {
          this.barcos.set(bs.filter(b => b.jugadorId === current.id));
        } else {
          this.barcos.set(bs);
        }
      },
      error: err => {
        console.error('Error cargando barcos', err);
        this.errorMsg.set('No fue posible cargar la lista de barcos.');
      }
    });
  }

  // Puente para [(ngModel)] en el select
  get selectedBarcoIdModel(): number | null {
    return this.selectedBarcoId();
  }
  set selectedBarcoIdModel(val: number | null) {
    this.selectedBarcoId.set(val);
  }

  seleccionarBarco(): void {
    const id = this.selectedBarcoId();
    if (!id) {
      this.barco.set(null);
      this.errorMsg.set('Seleccione un barco primero.');
      return;
    }

    const b = this.barcos().find(b => b.id === id) ?? null;
    this.barco.set(b);
    this.errorMsg.set(null);
  }

  mover(ax: number, ay: number): void {
    const b = this.barco();
    if (!b || !b.id) {
      this.errorMsg.set('Seleccione un barco primero.');
      return;
    }

    const req: MovimientoRequest = {
      barcoId: b.id,
      ax,
      ay
    };

    this.errorMsg.set(null);

    this.gameService.mover(req).subscribe({
      next: bActualizado => {
        // actualizar barco seleccionado
        this.barco.set(bActualizado);

        // sincronizar en la lista
        const lista = this.barcos().slice();
        const idx = lista.findIndex(bb => bb.id === bActualizado.id);
        if (idx >= 0) {
          lista[idx] = bActualizado;
          this.barcos.set(lista);
        }
      },
      error: err => {
        console.error('Error aplicando movimiento', err);
        this.errorMsg.set('Movimiento no válido o error en el servidor.');
      }
    });
  }
}
