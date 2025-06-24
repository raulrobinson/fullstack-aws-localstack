import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTableButtonComponent } from './create-table-button.component';

describe('CreateTableButtonComponent', () => {
  let component: CreateTableButtonComponent;
  let fixture: ComponentFixture<CreateTableButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateTableButtonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateTableButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
