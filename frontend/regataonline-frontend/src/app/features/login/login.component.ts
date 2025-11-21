import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';
import { JugadorService } from '../../core/services/jugador.service';
import { Jugador } from '../../core/models/jugador.model';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, NgIf],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  jugadores: Jugador[] = [];
  selectedId?: number;

  password: string = '';
  errorMsg?: string;

  constructor(
    private jugadorService: JugadorService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 🔹 Importante: limpiar cualquier token viejo
    this.authService.logout();

    // 🔹 Cargar jugadores para el combo
    this.jugadorService.listar().subscribe({
      next: js => this.jugadores = js,
      error: err => {
        console.error('Error cargando jugadores', err);
        this.errorMsg = 'No fue posible cargar la lista de jugadores.';
      }
    });
  }

  entrar(): void {
    this.errorMsg = undefined;

    if (!this.selectedId || !this.password) {
      this.errorMsg = 'Debe seleccionar un usuario y escribir la contraseña.';
      return;
    }

    const jugador = this.jugadores.find(j => j.id === this.selectedId);
    if (!jugador) {
      this.errorMsg = 'Jugador no encontrado.';
      return;
    }

    // Login con email + contraseña
    this.authService.login(jugador.email, this.password).subscribe({
      next: user => {
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.errorMsg = 'Credenciales inválidas.';
      }
    });
  }
}
