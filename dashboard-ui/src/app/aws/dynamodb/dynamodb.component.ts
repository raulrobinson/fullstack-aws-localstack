import { Component, inject, signal } from '@angular/core';
import { DynamodbService } from "@shared/services/dynamodb.service";
import { JsonPipe } from "@angular/common";
import { ToastrService } from 'ngx-toastr';
import { CreateClientFormComponent } from "./create-table/create-client-form/create-client-form.component";
import { ConfirmDialogComponent } from "./confirm-dialog/confirm-dialog.component";
import { LucideAngularModule, Trash } from 'lucide-angular';

@Component({
  selector: 'app-dynamodb',
  standalone: true,
  imports: [
    JsonPipe,
    CreateClientFormComponent,
    ConfirmDialogComponent,
    LucideAngularModule,
  ],
  templateUrl: './dynamodb.component.html',
  styleUrl: './dynamodb.component.scss'
})
export class DynamodbComponent {
  // Inyecciones
  public readonly dynamodb = inject(DynamodbService);
  public readonly toast = inject(ToastrService);

  // Signals reactivos
  tables = this.dynamodb.tables;
  items = this.dynamodb.items;
  selectedTable = signal<string>('');
  loading = signal(false);

  // Utilidades
  readonly trash = Trash;

  constructor() {
    this.fetchTables();
  }

  reloadPage() {
    window.location.reload();
  }

  fetchTables() {
    this.loading.set(true);
    this.dynamodb.listTables().subscribe({
      next: (res) => this.tables.set(res.tables),
      error: () => this.toast.error('Error cargando tablas'),
      complete: () => this.loading.set(false),
    });
  }

  fetchItems(table: string) {
    this.selectedTable.set(table);
    this.dynamodb.getItems(table).subscribe({
      next: (res) => this.items.set(res.items),
      error: () => this.toast.error('Error cargando items'),
    });
  }

  deleteTable(table: string) {
    // TO DO: llamar a dynamodb.deleteTable(table)
    this.toast.success(`Tabla "${table}" eliminada (simulado)`);
  }

  deleteItem(item: any) {
    // TO DO: llamar a dynamodb.deleteItem(table, item)
    this.toast.success(`Item eliminado (simulado)`);
  }

  getFieldsForTable(table: string): string[] {
    const first = this.items()?.[0];
    return first ? Object.keys(first) : [];
  }
}
