import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MapaService } from '../../core/services/mapa.service';
import { Mapa } from '../../core/models/mapa.model';
import { Celda, TipoCelda } from '../../core/models/celda.model';

@Component({
  selector: 'app-mapas',
  standalone: true,
  imports: [CommonModule, FormsModule, NgClass],
  templateUrl: './mapas.component.html',
  styleUrls: ['./mapas.component.css']
})
export class MapasComponent implements OnInit {

  // Estado del mapa como signal
  mapa = signal<Mapa>({
    nombre: 'Mapa editor',
    ancho: 12,
    alto: 10,
    celdas: []
  });

  // Herramienta actual (tipo de celda)
  tool = signal<TipoCelda>('AGUA');

  // Filas y columnas para el grid
  filas = computed(() => Array.from({ length: this.mapa().alto }, (_, i) => i));
  columnas = computed(() => Array.from({ length: this.mapa().ancho }, (_, i) => i));

  constructor(private mapaSrv: MapaService) {}

  ngOnInit(): void {}

  setTool(t: TipoCelda): void {
    this.tool.set(t);
  }

  onCellClick(x: number, y: number): void {
    const tipo = this.tool();

    this.mapa.update(m => {
      const nuevas = m.celdas.filter(c => !(c.x === x && c.y === y));
      nuevas.push({ x, y, tipo });
      return { ...m, celdas: nuevas };
    });
  }

  guardar(): void {
    const mapaActual = this.mapa();

    this.mapaSrv.crear(mapaActual).subscribe(m => {
      this.mapa.update(prev => ({
        ...prev,
        id: m.id
      }));
      alert('Mapa guardado con ID ' + m.id);
    });
  }

  getTipoCelda(x: number, y: number): TipoCelda {
    const m = this.mapa();
    const c = m.celdas.find(c => c.x === x && c.y === y);
    return c ? c.tipo : 'PARED';
  }
}
