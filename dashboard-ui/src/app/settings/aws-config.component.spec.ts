import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AwsConfigComponent } from './aws-config.component';

describe('AwsConfigComponent', () => {
  let component: AwsConfigComponent;
  let fixture: ComponentFixture<AwsConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AwsConfigComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AwsConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
