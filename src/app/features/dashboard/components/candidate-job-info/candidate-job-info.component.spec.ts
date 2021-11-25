import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateJobInfoComponent } from './candidate-job-info.component';

describe('CandidateJobInfoComponent', () => {
  let component: CandidateJobInfoComponent;
  let fixture: ComponentFixture<CandidateJobInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CandidateJobInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateJobInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
