import { Component, effect, inject } from '@angular/core';
import { LocalstackHealthService } from '@shared/services/localstack-health.service';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.scss'
})
export class OverviewComponent {
  private healthService = inject(LocalstackHealthService);
  readonly health = this.healthService.data;

  constructor() {
    this.healthService.fetchHealth();
    effect(() => console.log(this.health()));
  }

  stats() {
    const services = this.health()?.services || {};
    const counts = {
      running: 0,
      available: 0,
      disabled: 0,
      total: Object.keys(services).length
    };

    for (const status of Object.values(services)) {
      if (status === 'running') counts.running++;
      else if (status === 'available') counts.available++;
      else if (status === 'disabled') counts.disabled++;
    }

    return counts;
  }
}
