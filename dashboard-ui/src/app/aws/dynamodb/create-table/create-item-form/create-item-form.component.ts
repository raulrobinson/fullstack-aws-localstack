import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";

export interface ItemField {
  key: string;
  type: 'string' | 'number' | 'boolean'; // puedes expandir tipos
  required?: boolean;
}

@Component({
  selector: 'app-create-item-form',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './create-item-form.component.html',
  styleUrl: './create-item-form.component.scss'
})
export class CreateItemFormComponent {
  @Input() tableName = '';
  @Input() fields: ItemField[] = [];

  @Output() created = new EventEmitter<void>();

  item: { [key: string]: any } = {};

  submit() {
    // TODO: Lógica para llamar al servicio con this.tableName y this.item
    // this.dynamoService.putItem(this.tableName, this.item)
    this.created.emit();
    this.item = {};
  }
}
