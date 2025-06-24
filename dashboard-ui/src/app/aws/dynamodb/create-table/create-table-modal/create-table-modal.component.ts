import { Component, EventEmitter, Output, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { DynamodbService } from "@shared/services/dynamodb.service";

@Component({
  selector: 'app-create-table-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-table-modal.component.html',
  styleUrl: './create-table-modal.component.scss'
})
export class CreateTableModalComponent {
  @Output() close = new EventEmitter<void>();
  @Output() created = new EventEmitter<void>();

  tableName = '';
  primaryKey = 'id';

  private db = inject(DynamodbService);
  private toast = inject(ToastrService);

  async submit() {
    try {
      await this.db.createTable(this.tableName, this.primaryKey);
      this.toast.success('Tabla creada exitosamente');
      this.created.emit();
    } catch (err) {
      console.error(err);
      this.toast.error('Error creando tabla');
    }
  }
}
