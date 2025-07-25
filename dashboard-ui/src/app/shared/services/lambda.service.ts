import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LambdaInfo {
  functionName: string;
  runtime: string;
  handler: string;
}

@Injectable({
  providedIn: 'root'
})
export class LambdaService {
  private http = inject(HttpClient);

  //lambdas = signal<string[]>([]);
  response = signal<string | null>(null);

  lambdas = signal<LambdaInfo[]>([]);

  listLambdas(): Observable<LambdaInfo[]> {
    return this.http.get<LambdaInfo[]>('/api/lambda/list');
  }

  fetchLambdas() {
    this.listLambdas().subscribe({
      next: (lambdas) => this.lambdas.set(lambdas),
      error: (err) => console.error('❌ Error cargando lambdas:', err),
    });
  }

  /*listLambdas(): Observable<{ lambdas: string[] }> {
    return this.http.get<{ lambdas: string[] }>('/api/lambda/list');
  }

  fetchLambdas() {
    this.listLambdas().subscribe(res => this.lambdas.set(res.lambdas));
  }*/

  invokeLambda(name: string, payload: any): Observable<{ result: string }> {
    return this.http.post<{ result: string }>(`/api/lambda/invoke?name=${name}`, payload);
  }

  triggerLambda(name: string, payload: any) {
    this.invokeLambda(name, payload).subscribe(res => this.response.set(res.result));
  }
}
