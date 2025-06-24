import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceBrowserComponent } from './resource-browser.component';

describe('ResourceBrowserComponent', () => {
  let component: ResourceBrowserComponent;
  let fixture: ComponentFixture<ResourceBrowserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResourceBrowserComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ResourceBrowserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
