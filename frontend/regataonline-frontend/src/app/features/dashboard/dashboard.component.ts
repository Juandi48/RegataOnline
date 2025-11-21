import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../core/services/auth.service';
import { JugadorService } from '../../core/services/jugador.service';
import { BarcoService } from '../../core/services/barco.service';
import { ModeloBarcoService } from '../../core/services/modelo-barco.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  totalJugadores = 0;
  totalBarcos    = 0;
  totalModelos   = 0;
  carrerasActivas = 1;

  isAdmin   = false;
  isJugador = false;
  nombreUsuario = '';

  constructor(
    private auth: AuthService,
    private router: Router,
    private jugadorSrv: JugadorService,
    private barcoSrv: BarcoService,
    private modeloSrv: ModeloBarcoService
  ) {}

  ngOnInit(): void {
    this.isAdmin   = this.auth.isAdmin();
    this.isJugador = this.auth.isJugador();
    this.nombreUsuario = this.auth.currentUser?.nombre ?? '';

    this.cargarContadores();
  }

  cargarContadores(): void {
    this.jugadorSrv.listar().subscribe(js => this.totalJugadores = js.length);
    this.barcoSrv.listar().subscribe(bs => this.totalBarcos = bs.length);
    this.modeloSrv.listar().subscribe(ms => this.totalModelos = ms.length);
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  irJugadores(): void {
    this.router.navigate(['/jugadores']);
  }

  irBarcos(): void {
    this.router.navigate(['/barcos']);
  }

  irModelos(): void {
    this.router.navigate(['/modelos']);
  }

  irMapas(): void {
    this.router.navigate(['/mapas']);
  }

  irJuego(): void {
    this.router.navigate(['/juego']);
  }
}
