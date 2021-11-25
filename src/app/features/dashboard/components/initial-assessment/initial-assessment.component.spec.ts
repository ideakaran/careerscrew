import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InitialAssessmentComponent } from './initial-assessment.component';

describe('InitialAssessmentComponent', () => {
  let component: InitialAssessmentComponent;
  let fixture: ComponentFixture<InitialAssessmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InitialAssessmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InitialAssessmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
