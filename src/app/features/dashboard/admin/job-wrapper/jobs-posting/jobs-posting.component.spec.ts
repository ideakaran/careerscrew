import {JobWrapperComponent} from '@app/features/dashboard/admin/job-wrapper/job-wrapper.component';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {JobsPostingComponent} from '@app/features/dashboard/admin/job-wrapper/jobs-posting/jobs-posting.component';

describe('JobsPostingComponent', () => {
  let component: JobsPostingComponent;
  let fixture: ComponentFixture<JobsPostingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobsPostingComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsPostingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
