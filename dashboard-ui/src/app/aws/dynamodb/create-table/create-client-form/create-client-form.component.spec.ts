import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateClientFormComponent } from './create-client-form.component';

describe('CreateClientFormComponent', () => {
  let component: CreateClientFormComponent;
  let fixture: ComponentFixture<CreateClientFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateClientFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateClientFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
