import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessCandidateJobComponent } from './process-candidate-job.component';

describe('ProcessCandidateJobComponent', () => {
  let component: ProcessCandidateJobComponent;
  let fixture: ComponentFixture<ProcessCandidateJobComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProcessCandidateJobComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessCandidateJobComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
