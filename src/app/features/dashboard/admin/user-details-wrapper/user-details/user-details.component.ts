import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ActivatedRoute, convertToParamMap, Params, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {CandidateJob} from '@app/core/model/core.model';
import {APP_ROUTES} from '@app/core/core.constant';
import {CandidateService} from '@app/core/services/candidate.service';
import {Column} from '@app/shared/simple-table/simple-table.model';
import {CandidateJobColumn} from '@app/features/dashboard/admin/user-details-wrapper/user-details/user-details.model';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  CandidateJobColumn: Column[] = CandidateJobColumn;
  candidateJobs: CandidateJob[];
  isLoading = false;
  responseMessage: string;
  hasError = false;
  alert = false;

  @ViewChild('candidateResumeViewContent', { read: TemplateRef, static: true }) candidateResumeViewContent!: TemplateRef<any>;
  currentCandidateJobData: CandidateJob;

  constructor(private router: Router,
              private http: HttpClient,
              private route: ActivatedRoute,
              private candidateService: CandidateService,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.candidateService.getAllCandidateJobs().subscribe((candidateJobs: CandidateJob[]) => {
        this.candidateJobs = candidateJobs;
        // this.loginResponseMessage = 'Login Successful';
      }, err => {
        // this.hasError = true;
        // this.loginResponseMessage = err?.error?.response || 'Sorry! Something went wrong !!!';
      }
    );

    this.route.queryParams.subscribe((params: Params) => {
      const paramMap = convertToParamMap(params);
      this.hasError = paramMap.get('success') === 'true';
      if (this.hasError) {
        this.responseMessage = 'Updated Successfully';
        this.alert = true;
        setTimeout(() => {
          this.alert = false;
        }, 3000);
      }
    });
  }

  updateUser(candidateJob: CandidateJob): void {
    this.router.navigate([APP_ROUTES.DASH_USER_DETAILS_UPDATE, candidateJob.userEntity.id]);
  }

  viewUser(candidateJob: CandidateJob): void {
    this.router.navigate([APP_ROUTES.DASH_USER_DETAILS, candidateJob.userEntity.id]);
  }

  openCandidateResumeViewContent(candidateJob: CandidateJob): void {
    this.currentCandidateJobData = candidateJob;
    this.modalService.open(this.candidateResumeViewContent, {size: 'lg', centered: true, keyboard: true}).result.then((result) => {
      // .close()
    }, (reason) => {
      // .dismiss() - cross or escape
    });
  }

}
