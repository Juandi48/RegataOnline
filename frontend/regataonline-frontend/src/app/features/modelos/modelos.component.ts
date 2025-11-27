import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ModeloBarcoService } from '../../core/services/modelo-barco.service';
import { ModeloBarco } from '../../core/models/modelo-barco.model';

@Component({
  selector: 'app-modelos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './modelos.component.html',
  styleUrls: ['./modelos.component.css']
})
export class ModelosComponent implements OnInit {

  modelos = signal<ModeloBarco[]>([]);
  filtro = signal<string>('');
  errorMsg = signal<string | null>(null);

  // Lista filtrada basada en la señal filtro
  modelosFiltrados = computed(() => {
    const f = this.filtro().trim().toLowerCase();
    const lista = this.modelos();
    if (!f) return lista;
    return lista.filter(m =>
      m.nombre.toLowerCase().includes(f) ||
      m.colorHex.toLowerCase().includes(f)
    );
  });

  constructor(private modeloSrv: ModeloBarcoService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.errorMsg.set(null);
    this.modeloSrv.listar().subscribe({
      next: ms => this.modelos.set(ms),
      error: err => {
        console.error('Error cargando modelos', err);
        this.errorMsg.set('No fue posible cargar los modelos.');
      }
    });
  }

  // Puente para [(ngModel)]
  get filtroModel(): string {
    return this.filtro();
  }
  set filtroModel(val: string) {
    this.filtro.set(val);
  }

  nuevo(): void {
    const nombre = prompt('Nombre del modelo');
    if (!nombre) { return; }
    const colorHex = prompt('Color hex (#RRGGBB)', '#3498db') || '#3498db';

    this.modeloSrv.crear({ nombre, colorHex }).subscribe({
      next: () => this.cargar(),
      error: err => {
        console.error('Error creando modelo', err);
        this.errorMsg.set('No fue posible crear el modelo.');
      }
    });
  }

  editar(m: ModeloBarco): void {
    const nombre = prompt('Nuevo nombre', m.nombre);
    if (!nombre) { return; }
    const colorHex = prompt('Nuevo color', m.colorHex) || m.colorHex;

    this.modeloSrv.actualizar(m.id!, { nombre, colorHex }).subscribe({
      next: () => this.cargar(),
      error: err => {
        console.error('Error actualizando modelo', err);
        this.errorMsg.set('No fue posible actualizar el modelo.');
      }
    });
  }

  eliminar(m: ModeloBarco): void {
    if (!m.id) { return; }
    if (!confirm(`¿Eliminar modelo "${m.nombre}"?`)) { return; }

    this.modeloSrv.eliminar(m.id).subscribe({
      next: () => this.cargar(),
      error: err => {
        console.error('Error eliminando modelo', err);
        this.errorMsg.set('No fue posible eliminar el modelo.');
      }
    });
  }
}
