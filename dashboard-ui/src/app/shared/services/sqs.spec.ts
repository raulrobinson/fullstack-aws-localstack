import { TestBed } from '@angular/core/testing';

import { SqsService } from './sqs.service';

describe('SqsService', () => {
  let service: SqsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SqsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
