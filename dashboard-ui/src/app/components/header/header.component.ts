import { Component, inject, signal } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  router = inject(Router);
  showMenu = signal(false);
  toggleMenu = () => this.showMenu.update(v => !v);

  logout() {
    localStorage.removeItem('auth');
    this.router.navigate(['/login']);
  }
}
