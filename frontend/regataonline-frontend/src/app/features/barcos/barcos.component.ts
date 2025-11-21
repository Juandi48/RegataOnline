import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { BarcoService } from '../../core/services/barco.service';
import { JugadorService } from '../../core/services/jugador.service';
import { ModeloBarcoService } from '../../core/services/modelo-barco.service';
import { AuthService } from '../../core/services/auth.service';

import { Barco } from '../../core/models/barco.model';
import { Jugador } from '../../core/models/jugador.model';
import { ModeloBarco } from '../../core/models/modelo-barco.model';

@Component({
  selector: 'app-barcos',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf, NgClass],
  templateUrl: './barcos.component.html',
  styleUrls: ['./barcos.component.css']
})
export class BarcosComponent implements OnInit {

  barcos: Barco[] = [];
  jugadores: Jugador[] = [];
  modelos: ModeloBarco[] = [];

  selectedJugadorId?: number;
  selectedModeloId?: number;

  // 🔥 propiedades para permisos
  isAdmin = false;
  isJugador = false;

  constructor(
    private barcoSrv: BarcoService,
    private jugadorSrv: JugadorService,
    private modeloSrv: ModeloBarcoService,
    public auth: AuthService      // IMPORTANTE: debe ser PUBLIC
  ) {}

  ngOnInit(): void {
    // 🔥 Determinar rol del usuario
    this.isAdmin = this.auth.isAdmin();
    this.isJugador = this.auth.isJugador();

    // ADMIN → carga todos los jugadores
    if (this.isAdmin) {
      this.jugadorSrv.listar().subscribe(js => this.jugadores = js);
    }

    // Ambos roles cargan los modelos
    this.modeloSrv.listar().subscribe(ms => this.modelos = ms);

    this.cargar();
  }

  cargar(): void {
    this.barcoSrv.listar().subscribe(bs => {

      if (this.isAdmin) {
        this.barcos = bs;
      } else {
        const id = this.auth.currentUser?.id;
        this.barcos = bs.filter(b => b.jugadorId === id);
      }

    });
  }

  nuevo(): void {
    if (!this.selectedModeloId) {
      alert("Debe seleccionar un modelo");
      return;
    }

    let jugadorId = this.selectedJugadorId;

    if (this.isJugador) {
      jugadorId = this.auth.currentUser?.id;
    }

    if (!jugadorId) {
      alert("Debe seleccionar un jugador");
      return;
    }

    this.barcoSrv.crear({
      jugadorId,
      modeloId: this.selectedModeloId,
      velX: 0,
      velY: 0,
      posX: 0,
      posY: 0
    }).subscribe(() => this.cargar());
  }

  eliminar(id?: number): void {
    if (!id || this.isJugador) return;

    if (!confirm("¿Eliminar barco?")) return;

    this.barcoSrv.eliminar(id).subscribe(() => this.cargar());
  }
}
