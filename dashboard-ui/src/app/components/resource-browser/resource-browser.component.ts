import { Component, inject } from '@angular/core';
import { Router } from "@angular/router";
import { Resources } from "@shared/resources";

@Component({
  selector: 'app-resource-browser',
  standalone: true,
  imports: [],
  templateUrl: './resource-browser.component.html',
  styleUrl: './resource-browser.component.scss'
})
export class ResourceBrowserComponent {
  protected readonly router = inject(Router);
  resources = Resources;

  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}
