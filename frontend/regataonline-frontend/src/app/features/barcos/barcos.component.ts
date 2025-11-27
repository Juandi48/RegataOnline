import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
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
  imports: [CommonModule, FormsModule],
  templateUrl: './barcos.component.html',
  styleUrls: ['./barcos.component.css']
})
export class BarcosComponent implements OnInit {

  barcos = signal<Barco[]>([]);
  jugadores = signal<Jugador[]>([]);
  modelos = signal<ModeloBarco[]>([]);

  selectedJugadorId = signal<number | null>(null);
  selectedModeloId = signal<number | null>(null);

  isAdmin = signal(false);
  isJugador = signal(false);
  errorMsg = signal<string | null>(null);

  constructor(
    private barcoSrv: BarcoService,
    private jugadorSrv: JugadorService,
    private modeloSrv: ModeloBarcoService,
    public auth: AuthService
  ) { }

  ngOnInit(): void {
    this.isAdmin.set(this.auth.isAdmin());
    this.isJugador.set(this.auth.isJugador());

    // ADMIN → carga todos los jugadores
    if (this.isAdmin()) {
      this.jugadorSrv.listar().subscribe({
        next: js => this.jugadores.set(js),
        error: err => {
          console.error('Error cargando jugadores', err);
          this.errorMsg.set('No fue posible cargar los jugadores.');
        }
      });
    }

    // Ambos roles cargan los modelos
    this.modeloSrv.listar().subscribe({
      next: ms => this.modelos.set(ms),
      error: err => {
        console.error('Error cargando modelos', err);
        this.errorMsg.set('No fue posible cargar los modelos.');
      }
    });

    this.cargar();
  }

  cargar(): void {
    this.errorMsg.set(null);

    this.barcoSrv.listar().subscribe({
      next: bs => {
        if (this.isAdmin()) {
          this.barcos.set(bs);
        } else {
          const current = this.auth.currentUser();
          const id = current?.id ?? null;
          if (id == null) {
            this.barcos.set([]);
          } else {
            this.barcos.set(bs.filter(b => b.jugadorId === id));
          }
        }
      },
      error: err => {
        console.error('Error cargando barcos', err);
        this.errorMsg.set('No fue posible cargar los barcos.');
      }
    });
  }

  // Puentes para [(ngModel)]
  get selectedJugadorIdModel(): number | null {
    return this.selectedJugadorId();
  }
  set selectedJugadorIdModel(val: number | null) {
    this.selectedJugadorId.set(val);
  }

  get selectedModeloIdModel(): number | null {
    return this.selectedModeloId();
  }
  set selectedModeloIdModel(val: number | null) {
    this.selectedModeloId.set(val);
  }

  nuevo(): void {
    const modeloId = this.selectedModeloId();
    if (!modeloId) {
      alert('Debe seleccionar un modelo');
      return;
    }

    let jugadorId: number | null;

    if (this.isJugador()) {
      const current = this.auth.currentUser();
      jugadorId = current?.id ?? null;   // jugador siempre usa su propio id
    } else {
      // ADMIN: debe elegir el jugador en el combo
      jugadorId = this.selectedJugadorId();
      if (!jugadorId) {
        alert('Debe seleccionar un jugador');
        return;
      }
    }

    if (!jugadorId) {
      // fallback de seguridad, no debería pasar si lo de arriba está bien
      alert('No se pudo determinar el jugador.');
      return;
    }

    this.barcoSrv.crear({
      jugadorId,
      modeloId,
      velX: 0,
      velY: 0,
      posX: 0,
      posY: 0
    }).subscribe({
      next: () => this.cargar(),
      error: err => {
        console.error('Error creando barco', err);
        this.errorMsg.set('No fue posible crear el barco.');
      }
    });
  }


  eliminar(id?: number): void {
    if (!id || this.isJugador()) return;
    if (!confirm('¿Eliminar barco?')) return;

    this.barcoSrv.eliminar(id).subscribe({
      next: () => this.cargar(),
      error: err => {
        console.error('Error eliminando barco', err);
        this.errorMsg.set('No fue posible eliminar el barco.');
      }
    });
  }
}
