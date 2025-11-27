import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  // signals
  email    = signal<string>('');
  password = signal<string>('');
  errorMsg = signal<string | null>(null);
  loading  = signal<boolean>(false);

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // limpiar cualquier sesión previa
    this.authService.logout();
  }

  // Puentes para [(ngModel)]
  get emailModel(): string {
    return this.email();
  }
  set emailModel(val: string) {
    this.email.set(val);
  }

  get passwordModel(): string {
    return this.password();
  }
  set passwordModel(val: string) {
    this.password.set(val);
  }

  entrar(): void {
    this.errorMsg.set(null);

    const email    = this.email().trim();
    const password = this.password();

    if (!email || !password) {
      this.errorMsg.set('Debe ingresar usuario (email) y contraseña.');
      return;
    }

    this.loading.set(true);

    this.authService.login(email, password).subscribe({
      next: () => {
        this.loading.set(false);
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.loading.set(false);
        this.errorMsg.set('Credenciales inválidas.');
      }
    });
  }
}
