import {Component, effect, inject, signal} from '@angular/core';
import { SqsService } from "@shared/services/sqs.service";
import { FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-sqs',
  imports: [CommonModule, FormsModule],
  templateUrl: './sqs.html',
  standalone: true,
  styleUrl: './sqs.scss'
})
export class SqsComponent {
  public readonly toast = inject(ToastrService);
  protected queues = this.sqsService.queues;
  protected messages = this.sqsService.messages;
  protected selectedQueue = signal<string>('');
  protected messageBody = signal<string>('');

  constructor(private sqsService: SqsService) {
    this.sqsService.fetchQueues();

    // Limpiar mensajes al cambiar de cola
    effect(() => {
      this.selectedQueue();
      this.messages.set([]);
    });
  }

  sendMessage() {
    const queue = this.selectedQueue();
    const body = this.messageBody();
    if (!queue || !body) return;

    this.sqsService.sendMessage(queue, body).subscribe(() => {
      this.toast.success('Mensaje enviado ✅');
      this.messageBody.set('');
    });
  }

  loadMessages() {
    const queue = this.selectedQueue();
    if (!queue) return;
    this.sqsService.fetchMessages(queue);
  }
}
