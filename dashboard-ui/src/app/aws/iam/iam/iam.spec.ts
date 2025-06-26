import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Iam } from './iam';

describe('Iam', () => {
  let component: Iam;
  let fixture: ComponentFixture<Iam>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Iam]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Iam);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
