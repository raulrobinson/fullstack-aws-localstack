import { TestBed } from '@angular/core/testing';

import { LocalstackHealthService } from './localstack-health.service';

describe('LocalstackHealthService', () => {
  let service: LocalstackHealthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocalstackHealthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
