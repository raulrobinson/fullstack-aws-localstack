import {effect, inject, Injectable, signal} from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {catchError, map, Observable, of, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Inyectamos el cliente HTTP para realizar peticiones al backend
  private http = inject(HttpClient);

  private readonly TOKEN_KEY = 'auth_token';

  // Signals
  token = signal<string | null>(null);
  isAuthenticated = signal<boolean>(false);

  constructor() {
    effect(() => {
      this.hasValidToken().subscribe(valid => {
        this.isAuthenticated.set(valid);
      });
    });
  }

  login(email: string, password: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json',
    });

    return this.http.post<{ token: string }>(
      '/api/auth/login',
      { email, password },
      { headers }
    ).pipe(
      tap(response => {
        this.token.set(response.token);
        this.isAuthenticated.set(true);
        localStorage.setItem('auth_token', response.token);
      })
    );
  }

  logout() {
    const token = this.getToken();
    return this.http.post('/api/auth/logout', {}, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    }).pipe(
      tap(() => {
        localStorage.removeItem(this.TOKEN_KEY);
        this.isAuthenticated.set(false);
      })
    );
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  hasValidToken(): Observable<boolean> {
    const token = this.getToken();
    if (!token) return of(false);

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    return this.http.get<{ valid: boolean }>('/api/auth/validate', { headers }).pipe(
      map(response => response.valid),
      catchError(err => {
        console.warn('❌ Token validation failed:', err);
        return of(false);
      })
    );
  }
}
