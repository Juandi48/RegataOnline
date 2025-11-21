import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MapaService } from '../../core/services/mapa.service';
import { Mapa } from '../../core/models/mapa.model';
import { Celda, TipoCelda } from '../../core/models/celda.model';

@Component({
  selector: 'app-mapas',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf, NgClass],
  templateUrl: './mapas.component.html',
  styleUrls: ['./mapas.component.css']
})

export class MapasComponent implements OnInit {

  mapa: Mapa = {
    nombre: 'Mapa editor',
    ancho: 12,
    alto: 10,
    celdas: []
  };

  tool: TipoCelda = 'AGUA';

  constructor(private mapaSrv: MapaService) {}

  ngOnInit(): void {}

  setTool(t: TipoCelda): void {
    this.tool = t;
  }

  onCellClick(x: number, y: number): void {
    const idx = this.mapa.celdas.findIndex(c => c.x === x && c.y === y);
    if (idx >= 0) {
      this.mapa.celdas.splice(idx, 1);
    }
    this.mapa.celdas.push({ x, y, tipo: this.tool });
  }

  guardar(): void {
    this.mapaSrv.crear(this.mapa).subscribe(m => {
      this.mapa.id = m.id;
      alert('Mapa guardado con ID ' + m.id);
    });
  }

  trackByCoord(_index: number, c: Celda): string {
    return `${c.x}-${c.y}`;
  }

  getTipoCelda(x: number, y: number): TipoCelda {
    const c = this.mapa.celdas.find(c => c.x === x && c.y === y);
    return c ? c.tipo : 'PARED';
  }
}
