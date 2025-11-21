import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf, NgStyle } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ModeloBarcoService } from '../../core/services/modelo-barco.service';
import { ModeloBarco } from '../../core/models/modelo-barco.model';

@Component({
  selector: 'app-modelos',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf, NgStyle],
  templateUrl: './modelos.component.html',
  styleUrls: ['./modelos.component.css']
})

export class ModelosComponent implements OnInit {

  modelos: ModeloBarco[] = [];
  filtro = '';

  constructor(private modeloSrv: ModeloBarcoService) {}

  ngOnInit(): void {
    this.cargar();
  }

  get modelosFiltrados(): ModeloBarco[] {
    const f = this.filtro.toLowerCase();
    return this.modelos.filter(m =>
      !f || m.nombre.toLowerCase().includes(f) || m.colorHex.toLowerCase().includes(f)
    );
  }

  cargar(): void {
    this.modeloSrv.listar().subscribe(ms => this.modelos = ms);
  }

  nuevo(): void {
    const nombre = prompt('Nombre del modelo');
    if (!nombre) { return; }
    const colorHex = prompt('Color hex (#RRGGBB)', '#3498db') || '#3498db';
    this.modeloSrv.crear({ nombre, colorHex }).subscribe(() => this.cargar());
  }

  editar(m: ModeloBarco): void {
    const nombre = prompt('Nuevo nombre', m.nombre);
    if (!nombre) { return; }
    const colorHex = prompt('Nuevo color', m.colorHex) || m.colorHex;
    this.modeloSrv.actualizar(m.id!, { nombre, colorHex }).subscribe(() => this.cargar());
  }

  eliminar(m: ModeloBarco): void {
    if (!m.id) { return; }
    if (!confirm(`¿Eliminar modelo "${m.nombre}"?`)) { return; }
    this.modeloSrv.eliminar(m.id).subscribe(() => this.cargar());
  }
}
