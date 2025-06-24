import { Component, EventEmitter, Output, signal } from '@angular/core';
import { CreateTableModalComponent } from "../create-table-modal/create-table-modal.component";

@Component({
  selector: 'app-create-table-button',
  standalone: true,
  imports: [CreateTableModalComponent],
  templateUrl: './create-table-button.component.html',
  styleUrl: './create-table-button.component.scss'
})
export class CreateTableButtonComponent {
  @Output() tableCreated = new EventEmitter<void>();
  showModal = signal(false);

  handleCreated() {
    this.showModal.set(false);
    this.tableCreated.emit();
  }
}
