import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [

  ],
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.scss'
})
export class ConfirmDialogComponent implements AfterViewInit {
  @Input() message = '¿Estás seguro?';
  @Output() confirm = new EventEmitter<void>();

  showConfirm = false;

  ngAfterViewInit() {
    const slot = document.querySelector('app-confirm-dialog > *');
    slot?.addEventListener('click', () => this.showConfirm = true);
  }

  cancel() {
    this.showConfirm = false;
  }

  confirmAction() {
    this.confirm.emit();
    this.showConfirm = false;
  }
}
