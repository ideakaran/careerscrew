import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {CandidateService} from '@app/core/services/candidate.service';
import {combineLatest, Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {UserService} from '@app/core/services/user.service';
import {
  CandidateAssessment,
  CandidateInterview,
  CandidateJob,
  CandidateProfile,
  CandidateRemark,
  Interviewer,
  User
} from '@app/core/model/core.model';
import {ActivatedRoute} from '@angular/router';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-process-candidate-job',
  templateUrl: './process-candidate-job.component.html',
  styleUrls: ['./process-candidate-job.component.scss']
})
export class ProcessCandidateJobComponent implements OnInit, OnDestroy {

  tabNumber = 1;
  hasError = false;

  candidateUser: User;
  candidateJob: CandidateJob;
  candidateInterview: CandidateInterview;
  candidateProfile: CandidateProfile;
  candidateRemark: CandidateRemark;

  private unsubscribe = new Subject<void>();
  responseMessage: any;
  interviewers: Interviewer[];

  modalResponseMessage: string;
  isLoading = false;

  rejectedCandidateName: string;
  @ViewChild('rejectionContent', {read: TemplateRef, static: true}) rejectionContent!: TemplateRef<any>;

  constructor(
    private userService: UserService,
    private candidateService: CandidateService,
    private modalService: NgbModal,
    private route: ActivatedRoute) {
  }

  // TAB_VIEW = {INITIAL_PHASE: 1, INTERVIEW_SCHEDULED: 2, CANDIDATE_PROFILE: 3, REMARKS: 4};

  ngOnInit(): void {
    const userId = this.route.snapshot.params['id'];
    this.getCandidateCurrentJobByUserId(userId);
    this.getAllInterviewers();
  }

  onTabChange(tabNumber: number): void {
    this.tabNumber = tabNumber;
    this.hasError = false;
    this.responseMessage = null;
  }

  initialAssessmentValue(event): void {
    const candidateAssessment: CandidateAssessment = {
      assignment: event?.description,
      id: this.candidateJob.id,
    };
    this.candidateService.updateCandidateAssessment(candidateAssessment).subscribe(res => {
      this.hasError = false;
      this.responseMessage = 'Successfully Sent';
    },
    err => {
      this.hasError = true;
      this.responseMessage = err?.error?.response || 'Sorry! Something went wrong !!!';
    });
  }

  interviewScheduleValue(event): void {
    const candidateRemark: CandidateInterview = {
      id: event.id,
      stage: event?.stage,
      interviewDate: event?.interviewDate + 'T' + event?.time + ':00',
      description: event?.description,
      candidateJobId: this.candidateJob?.id,
      interviewersId: event?.interviewers,
    };
    this.candidateService.updateCandidateInterview(candidateRemark).subscribe(res => {
      this.hasError = false;
      this.responseMessage = 'Successfully Scheduled';
    },
    err => {
      this.hasError = true;
      this.responseMessage = err?.error?.response || 'Sorry! Something went wrong !!!';
    });
  }

  finalReviewValue(event): void {
    const candidateRemark: CandidateRemark = {
      id: event?.id,
      remarks: event?.remarks,
      tackling: event?.tackling,
      communication: event?.communication,
      stability: event?.stability,
      grace: event?.grace,
      writtenTestScore: event?.writtenTestScore,
      isSuitableForHiring: event?.isSuitableForHiring,
      personality: event?.personality,
      knowledge: event?.knowledge,
      candidateJobId: this.candidateJob.id,
    };
    this.candidateService.updateCandidateRemark(candidateRemark).subscribe(res => {
      this.hasError = false;
      this.responseMessage = 'Successfully Saved';
    },
    err => {
      this.hasError = true;
      this.responseMessage = err?.error?.response || 'Sorry! Something went wrong !!!';
    });
  }

  getCandidateCurrentJobByUserId(userId: number): void {
    this.candidateService.getCandidateCurrentJobByUserId(userId).subscribe((candidateJob: CandidateJob) => {
      this.candidateUser = candidateJob.userEntity;
      this.candidateJob = candidateJob;
      this.processCandidateInfo(candidateJob.id);
      console.log(candidateJob);
    }, error => {
      console.log(error);
    });
  }

  getAllInterviewers(): void {
    this.candidateService.getAllInterviewers().subscribe((interviewers: Interviewer[]) => {
      this.interviewers = interviewers;
      console.log(interviewers);
    }, error => {
      console.log(error);
    });
  }

  processCandidateInfo(candidateJobId): void {
    combineLatest([
      this.candidateService.getCandidateInterviewByCandidateJobId(candidateJobId),
      this.candidateService.getCandidateProfileByCandidateJobId(candidateJobId),
      this.candidateService.getCandidateRemarkByCandidateJobId(candidateJobId)
    ]).pipe(
      takeUntil(this.unsubscribe)
    ).subscribe(([candidateInterview, candidateProfile, candidateRemark]) => {
      this.hasError = false;
      this.candidateInterview = candidateInterview;
      this.candidateProfile = candidateProfile;
      this.candidateRemark = candidateRemark;
    }, error => {
      this.hasError = true;
    });
  }

  openRejectionContent(): void {
    this.modalService.open(this.rejectionContent, {size: 'md', centered: true, keyboard: true}).result.then((result) => {
      // .close()
    }, (reason) => {
      // .dismiss() - cross or escape
    });
  }

  submitRejectApplication(): void {
    this.isLoading = true;
    this.candidateService.rejectCandidateApplication(this.candidateJob)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((candidateJob) => {
        this.modalResponseMessage = 'Action complete. This candidate has been rejected';
        this.isLoading = false;
        this.hasError = false;
      }, error => {
        this.isLoading = false;
        this.hasError = true;
        this.modalResponseMessage = 'Something went wrong while replying';
      });
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

}
