import { Component, inject, signal } from '@angular/core';
import { LambdaService } from '@shared/services/lambda.service';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-lambda.component',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './lambda.component.html',
  styleUrl: './lambda.component.scss'
})
export class LambdaComponent {
  private readonly lambdaService = inject(LambdaService);
  private readonly toast = inject(ToastrService);

  lambdas = this.lambdaService.lambdas;
  response = this.lambdaService.response;
  //selected = signal('');
  selectedLambda = signal<string>('');
  payload = signal('{}');

  constructor() {
    this.lambdaService.fetchLambdas();
  }

  invoke() {
    try {
      const body = JSON.parse(this.payload());
      this.lambdaService.triggerLambda(this.selectedLambda(), body);
    } catch {
      this.toast.error('Payload inválido (debe ser JSON)');
    }
  }
}
