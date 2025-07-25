import { TestBed } from '@angular/core/testing';

import { LambdaService } from './lambda.service';

describe('LambdaService', () => {
  let service: LambdaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LambdaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
