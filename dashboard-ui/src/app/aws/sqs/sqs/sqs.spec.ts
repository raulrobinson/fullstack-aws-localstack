import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sqs } from './sqs';

describe('SqsService', () => {
  let component: Sqs;
  let fixture: ComponentFixture<Sqs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Sqs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sqs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
