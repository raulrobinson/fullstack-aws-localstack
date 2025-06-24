import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { DynamodbService } from "@shared/services/dynamodb.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-create-client-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './create-client-form.component.html',
  styleUrl: './create-client-form.component.scss'
})
export class CreateClientFormComponent {
  @Output() created = new EventEmitter<void>();

  name = '';

  private db = inject(DynamodbService);
  private toast = inject(ToastrService);

  submit() {
    const id = crypto.randomUUID(); // usa cualquier generador si prefieres

    const item = {
      id: { S: id },
      name: { S: this.name },
    };

    /*this.db.putItem('Clients', item).subscribe({
      next: () => {
        this.toast.success('Cliente agregado');
        this.created.emit();
        this.name = '';
      },
      error: () => this.toast.error('Error al guardar'),
    });*/
  }
}
