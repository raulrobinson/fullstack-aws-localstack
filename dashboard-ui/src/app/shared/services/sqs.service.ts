import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SqsService {
  private http = inject(HttpClient);
  private token = localStorage.getItem('auth_token');

  queues = signal<string[]>([]);

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.token}`,
    });
  }

  listQueues(): Observable<{ queues: string[] }> {
    return this.http.get<{ queues: string[] }>('/api/sqs/list', {
      headers: this.getHeaders()
    });
  }

  fetchQueues() {
    this.listQueues().subscribe(res => this.queues.set(res.queues));
  }

  /*private readonly client: SQSClient;

  constructor() {
    this.client = new SQSClient({
      region: 'us-east-1',
      endpoint: 'http://localhost:4566', // LocalStack endpoint
      credentials: {
        accessKeyId: 'test',
        secretAccessKey: 'test'
      }
    });
  }

  // ✅ 1. Listar colas
  listQueues(): Observable<{ QueueUrls?: string[] }> {
    return from(this.client.send(new ListQueuesCommand({})));
  }

  // ✅ 2. Enviar mensaje
  sendMessage(queueUrl: string, messageBody: string): Observable<any> {
    return from(this.client.send(new SendMessageCommand({
      QueueUrl: queueUrl,
      MessageBody: messageBody
    })));
  }

  // ✅ 3. Recibir mensajes
  receiveMessages(queueUrl: string, maxNumberOfMessages = 5): Observable<any> {
    return from(this.client.send(new ReceiveMessageCommand({
      QueueUrl: queueUrl,
      MaxNumberOfMessages: maxNumberOfMessages,
      WaitTimeSeconds: 2 // para simular long polling en LocalStack
    })));
  }

  // ✅ 4. Borrar mensaje de la cola
  deleteMessage(queueUrl: string, receiptHandle: string): Observable<any> {
    return from(this.client.send(new DeleteMessageCommand({
      QueueUrl: queueUrl,
      ReceiptHandle: receiptHandle
    })));
  }*/
}
