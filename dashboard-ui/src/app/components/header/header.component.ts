import { Component, inject, signal } from '@angular/core';
import { Router } from "@angular/router";
import { AuthService } from "@shared/services/auth.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  private router = inject(Router);
  private authService = inject(AuthService);
  public readonly toast = inject(ToastrService);

  showMenu = signal(false);
  toggleMenu = () => this.showMenu.update(v => !v);

  logout() {
    this.authService.logout().subscribe({
      next: () => {
        this.toast.success('Sesión cerrada con éxito');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.toast.error('Error al cerrar sesión');
        console.error('❌ Error al cerrar sesión:', err);
      }
    });
  }
}
