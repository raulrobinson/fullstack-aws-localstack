import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTableModalComponent } from './create-table-modal.component';

describe('CreateTableModalComponent', () => {
  let component: CreateTableModalComponent;
  let fixture: ComponentFixture<CreateTableModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateTableModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateTableModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
