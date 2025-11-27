import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../core/services/auth.service';
import { BarcoService } from '../../core/services/barco.service';
import { JugadorService } from '../../core/services/jugador.service';
import { ModeloBarcoService } from '../../core/services/modelo-barco.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  imports: [CommonModule]
})
export class DashboardComponent implements OnInit {

  totalJugadores = signal(0);
  totalBarcos    = signal(0);
  totalModelos   = signal(0);

  constructor(
    public auth: AuthService,
    private router: Router,
    private jugadorSrv: JugadorService,
    private barcoSrv: BarcoService,
    private modeloSrv: ModeloBarcoService
  ) {}

  ngOnInit(): void {
    if (this.auth.isAdmin()) {
      // Admin: totales globales
      this.jugadorSrv.listar().subscribe(js => this.totalJugadores.set(js.length));
      this.barcoSrv.listar().subscribe(bs => this.totalBarcos.set(bs.length));
      this.modeloSrv.listar().subscribe(ms => this.totalModelos.set(ms.length));
    } else {
      // Jugador: cuenta solo sus barcos
      const id = this.auth.currentUser()?.id;
      if (id != null) {
        this.barcoSrv.listar().subscribe(bs => {
          this.totalBarcos.set(bs.filter(b => b.jugadorId === id).length);
        });
      }
    }
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  irJugadores() { this.router.navigate(['/jugadores']); }
  irBarcos()    { this.router.navigate(['/barcos']); }
  irModelos()   { this.router.navigate(['/modelos']); }
  irMapas()     { this.router.navigate(['/mapas']); }
  irJuego()     { this.router.navigate(['/juego']); }
}
