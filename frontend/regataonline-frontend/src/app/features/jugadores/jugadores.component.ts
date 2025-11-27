import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { JugadorService } from '../../core/services/jugador.service';
import { Jugador } from '../../core/models/jugador.model';

@Component({
  selector: 'app-jugadores',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './jugadores.component.html',
  styleUrls: ['./jugadores.component.css']
})
export class JugadoresComponent implements OnInit {

  jugadores = signal<Jugador[]>([]);
  cargando = signal(false);
  errorMsg = signal<string | null>(null);

  constructor(private jugadorService: JugadorService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando.set(true);
    this.errorMsg.set(null);

    this.jugadorService.listar().subscribe({
      next: js => {
        this.jugadores.set(js);
        this.cargando.set(false);
      },
      error: err => {
        console.error('Error cargando jugadores', err);
        this.errorMsg.set('No fue posible cargar los jugadores.');
        this.cargando.set(false);
      }
    });
  }

  nuevo(): void {
    const nombre = prompt('Nombre');
    if (!nombre) { return; }

    const email = prompt('Email') || '';
    if (!email) { return; }

    this.jugadorService.crear({ nombre, email }).subscribe({
      next: () => this.cargar(),
      error: err => {
        console.error('Error creando jugador', err);
        this.errorMsg.set('No fue posible crear el jugador.');
      }
    });
  }

  eliminar(id?: number): void {
    if (!id) { return; }
    if (!confirm('¿Eliminar jugador?')) { return; }

    this.jugadorService.eliminar(id).subscribe({
      next: () => this.cargar(),
      error: err => {
        console.error('Error eliminando jugador', err);
        this.errorMsg.set('No fue posible eliminar el jugador.');
      }
    });
  }
}
