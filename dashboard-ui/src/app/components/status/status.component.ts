import { Component, effect, inject } from '@angular/core';
import { LocalstackHealthService } from "@shared/services/localstack-health.service";
import { Router } from "@angular/router";
import { ServiceRouteMap } from "@shared/resources";

@Component({
  selector: 'app-status',
  standalone: true,
  imports: [],
  templateUrl: './status.component.html',
  styleUrl: './status.component.scss'
})
export class StatusComponent {
  protected readonly Object = Object;

  private router = inject(Router);
  private healthService = inject(LocalstackHealthService);
  readonly health = this.healthService.data;

  private serviceRouteMap: Record<string, string> = ServiceRouteMap;

  constructor() {
    this.healthService.fetchHealth();
    effect(() => console.log(this.health()));
  }

  getStatusEmoji(status: string): string {
    switch (status) {
      case 'running': return '🟢';
      case 'available': return '🟡';
      case 'disabled': return '🔴';
      default: return '⚪️';
    }
  }

  navigateIfRunning(key: string) {
    const status = this.health()?.services?.[key];
    const route = this.serviceRouteMap[key];

    if (status === 'running' && route) {
      this.router.navigate([route]);
    }
  }
}
