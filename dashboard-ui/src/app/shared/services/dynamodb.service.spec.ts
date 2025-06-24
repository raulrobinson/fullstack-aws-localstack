import { TestBed } from '@angular/core/testing';

import { DynamodbService } from './dynamodb.service';

describe('DynamodbService', () => {
  let service: DynamodbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DynamodbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
