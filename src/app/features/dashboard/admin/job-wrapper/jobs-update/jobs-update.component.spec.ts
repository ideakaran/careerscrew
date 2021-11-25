import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobsUpdateComponent } from './jobs-update.component';

describe('JobsUpdateComponent', () => {
  let component: JobsUpdateComponent;
  let fixture: ComponentFixture<JobsUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobsUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
