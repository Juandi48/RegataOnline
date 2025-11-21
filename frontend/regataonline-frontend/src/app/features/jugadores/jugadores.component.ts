import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { JugadorService } from '../../core/services/jugador.service';
import { Jugador } from '../../core/models/jugador.model';

@Component({
  selector: 'app-jugadores',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor],
  templateUrl: './jugadores.component.html',
  styleUrls: ['./jugadores.component.css']
})

export class JugadoresComponent implements OnInit {

  jugadores: Jugador[] = [];

  constructor(private jugadorService: JugadorService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.jugadorService.listar().subscribe(js => this.jugadores = js);
  }

  nuevo(): void {
    const nombre = prompt('Nombre');
    if (!nombre) { return; }
    const email = prompt('Email') || '';
    this.jugadorService.crear({ nombre, email }).subscribe(() => this.cargar());
  }

  eliminar(id?: number): void {
    if (!id) { return; }
    if (!confirm('¿Eliminar jugador?')) { return; }
    this.jugadorService.eliminar(id).subscribe(() => this.cargar());
  }
}
