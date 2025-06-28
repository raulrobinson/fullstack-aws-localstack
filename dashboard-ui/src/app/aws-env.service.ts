import { computed, Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AwsEnvService {
  private selected = signal(localStorage.getItem('aws-env') || 'LocalStack');

  readonly selectedEnv = computed(() => this.selected());

  setEnv(value: string) {
    this.selected.set(value);
    localStorage.setItem('aws-env', value);
  }

  isLocal() {
    return this.selected() === 'LocalStack';
  }

  getEndpoint(service: 'sqs' | 's3' | 'dynamodb'): string {
    if (this.isLocal()) {
      switch (service) {
        case 'sqs':
          return 'http://localhost:4566/000000000000/your-queue';
        case 's3':
          return 'http://localhost:4566/your-bucket';
        case 'dynamodb':
          return 'http://localhost:4566';
      }
    } else {
      switch (service) {
        case 'sqs':
          return 'https://sqs.us-east-1.amazonaws.com/your-account-id/your-queue';
        case 's3':
          return 'https://your-bucket.s3.amazonaws.com';
        case 'dynamodb':
          return 'https://dynamodb.us-east-1.amazonaws.com';
      }
    }
  }

  /*getAwsEnv(): 'LocalStack' | 'AWS Real' {
    return (localStorage.getItem('aws-env') || 'LocalStack') as 'LocalStack' | 'AWS Real';
  }

  isLocal(): boolean {
    return this.getAwsEnv() === 'LocalStack';
  }*/
}
