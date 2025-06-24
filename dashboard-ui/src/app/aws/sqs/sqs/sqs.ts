import { Component, inject, OnInit, signal } from '@angular/core';
import { SqsService } from "@shared/services/sqs.service";
import {DynamodbService} from "@shared/services/dynamodb.service";

@Component({
  selector: 'app-sqs',
  imports: [],
  templateUrl: './sqs.html',
  standalone: true,
  styleUrl: './sqs.scss'
})
export class SqsComponent implements OnInit {

  private sqsService = inject(SqsService);

  constructor(public dynamo: DynamodbService, public sqs: SqsService) {
    this.dynamo.fetchTables();
    this.sqs.fetchQueues();
  }

  loadItems(table: string) {
    this.dynamo.fetchItems(table);
  }

  /*private sqsService = inject(SqsService);

  queues = signal<string[]>([]);
  selectedQueue = signal<string | null>(null);
  messageBody = signal('');
  messages = signal<{ Body: string; MessageId: string }[]>([]);*/

  /*constructor() {
    //this.loadQueues();
  }*/

  ngOnInit() {
    /*this.sqsService.listQueues().subscribe(res => {
      console.log('Colas disponibles:', res.QueueUrls);
    });

    this.sqsService.receiveMessages('http://localhost:4566/000000000000/my-queue')
      .subscribe(res => {
        console.log('Mensajes:', res.Messages);
      });*/
  }

  /*loadQueues() {
    this.http.get<{ queues: string[] }>('/api/sqs/list').subscribe((res) => {
      this.queues.set(res.queues);
    });
  }*/

}
