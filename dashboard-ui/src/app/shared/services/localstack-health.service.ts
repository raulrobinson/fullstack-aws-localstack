import { computed, inject, Injectable, signal } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LocalstackHealthService {
  private http = inject(HttpClient);
  private readonly healthUrl = 'http://localhost:4566/_localstack/health';
  private readonly rawData = signal<any | null>(null);

  data = computed(() => this.rawData());

  fetchHealth() {
    this.http.get(this.healthUrl).subscribe({
      next: (res: any) => this.rawData.set(res),
      error: err => console.error('Error fetching LocalStack health:', err)
    });
  }
}
