import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';


import { BarcoService } from '../../core/services/barco.service';
import { JugadorService } from '../../core/services/jugador.service';
import { ModeloBarcoService } from '../../core/services/modelo-barco.service';

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

  constructor(
    private barcoSrv: BarcoService,
    private jugadorSrv: JugadorService,
    private modeloSrv: ModeloBarcoService
  ) {}

  ngOnInit(): void {
    this.jugadorSrv.listar().subscribe(js => this.jugadores = js);
    this.modeloSrv.listar().subscribe(ms => this.modelos = ms);
    this.cargar();
  }

  cargar(): void {
    this.barcoSrv.listar().subscribe(bs => this.barcos = bs);
  }

  nuevo(): void {
    if (!this.selectedJugadorId || !this.selectedModeloId) {
      alert('Selecciona jugador y modelo');
      return;
    }

    this.barcoSrv.crear({
      jugadorId: this.selectedJugadorId,
      modeloId: this.selectedModeloId,
      velX: 0,
      velY: 0,
      posX: 0,
      posY: 0
    }).subscribe(() => this.cargar());
  }

  eliminar(id?: number): void {
    if (!id) { return; }
    if (!confirm('¿Eliminar barco?')) { return; }
    this.barcoSrv.eliminar(id).subscribe(() => this.cargar());
  }
}
