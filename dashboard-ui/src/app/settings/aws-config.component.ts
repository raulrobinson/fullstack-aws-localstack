import { Component, signal } from '@angular/core';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-aws-config.component',
  imports: [
    FormsModule,
  ],
  templateUrl: './aws-config.component.html',
  styleUrl: './aws-config.component.scss'
})
export class AwsConfigComponent {
  awsOptions = ['LocalStack', 'AWS Real'];
  selectedOption = signal(localStorage.getItem('aws-env') || 'LocalStack');

  onChange(option: string) {
    this.selectedOption.set(option);
    localStorage.setItem('aws-env', option);
  }

  handleChange(event: Event) {
    const select = event.target as HTMLSelectElement | null;
    const value = select?.value;
    if (value) {
      this.onChange(value);
    }
  }
}
