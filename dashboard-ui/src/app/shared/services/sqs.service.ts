import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SqsService {
  private http = inject(HttpClient);

  queues = signal<string[]>([]);
  messages = signal<{ Body: string; MessageId: string }[]>([]);

  fetchQueues(): void {
    this.http.get<{ queues: string[] }>('/api/sqs/list').subscribe(res => {
      this.queues.set(res.queues);
    });
  }

  sendMessage(queueUrl: string, messageBody: string): Observable<any> {
    return this.http.post('/api/sqs/send', { queueUrl, messageBody });
  }

  fetchMessages(queueUrl: string): void {
    this.http.post<{ messages: { Body: string; MessageId: string }[] }>(
      '/api/sqs/receive',
      { queueUrl }
    ).subscribe(res => {
      this.messages.set(res.messages);
    });
  }
}
