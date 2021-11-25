import {ComponentFixture, TestBed} from '@angular/core/testing';
import {JobWrapperComponent} from '@app/features/dashboard/admin/job-wrapper/job-wrapper.component';

describe('JobWrapperComponent', () => {
  let component: JobWrapperComponent;
  let fixture: ComponentFixture<JobWrapperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobWrapperComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
