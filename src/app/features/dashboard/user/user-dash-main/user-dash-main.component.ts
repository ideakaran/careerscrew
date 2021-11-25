import {Component, OnInit} from '@angular/core';
import {Credentials, CredentialsService} from '@app/auth/services/credentials.service';
import {CandidateJob, User} from '@app/core/model/core.model';
import {CandidateService} from '@app/core/services/candidate.service';
import {ApiEndpoints} from '@app/core/app-url.constant';

@Component({
  selector: 'app-user-dash-main',
  templateUrl: './user-dash-main.component.html',
  styleUrls: ['./user-dash-main.component.scss']
})
export class UserDashMainComponent implements OnInit {

  user: User;
  candidateJob: CandidateJob;
  userId: number;

  candidateResumeUrl = ApiEndpoints.CANDIDATE + '/' + 'resume.pdf';

  constructor(private credentialService: CredentialsService, private candidateService: CandidateService) {
  }

  ngOnInit(): void {
    const credentials: Credentials = this.credentialService.getCredentials();
    this.userId = credentials.userEntity?.id;
    this.getCandidateCurrentJobByUserId(this.userId);
  }

  getCandidateCurrentJobByUserId(userId: number): void {
    this.candidateService.getCandidateCurrentJobByUserId(userId).subscribe((candidateJob: CandidateJob) => {
      this.user = candidateJob.userEntity;
      this.candidateJob = candidateJob;
      console.log(candidateJob);
    }, error => {
      console.log(error);
    });
  }

}
