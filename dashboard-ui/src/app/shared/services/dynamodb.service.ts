import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DynamodbService {
  // Inyectamos el cliente HTTP para realizar peticiones al backend
  private http = inject(HttpClient);
  private token = localStorage.getItem('auth_token');

  // Usamos signals para manejar el estado de las tablas e items
  tables = signal<string[]>([]);
  items = signal<Record<string, any>[]>([]);

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.token}`,
    });
  }

  // ✅ Ir al backend para listar las tablas de DynamoDB
  listTables(): Observable<{ tables: string[] }> {
    return this.http.get<{ tables: string[] }>('/api/dynamodb/tables', {
      headers: this.getHeaders()
    });
  }

  // ✅ Listar tablas de DynamoDB
  fetchTables() {
    this.listTables().subscribe(res => this.tables.set(res.tables));
  }

  // ✅ Ir al backend para obtener los items de una tabla específica
  getItems(table: string): Observable<{ items: Record<string, any>[] }> {
    return this.http.get<{ items: Record<string, any>[] }>(`/api/dynamodb/items/${table}`, {
      headers: this.getHeaders()
    });
  }

  // ✅ Obtener items de una tabla específica
  fetchItems(table: string) {
    this.getItems(table).subscribe(res => this.items.set(res.items));
  }
}
