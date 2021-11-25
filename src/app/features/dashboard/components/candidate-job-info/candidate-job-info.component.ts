import {Component, Input, OnInit} from '@angular/core';
import {ApplicationStatus, CandidateJob, User, UserRole} from '@app/core/model/core.model';
import {UserService} from '@app/core/services/user.service';
import {ActivatedRoute} from '@angular/router';
import {CandidateService} from '@app/core/services/candidate.service';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {CredentialsService} from '@app/auth/services/credentials.service';

@Component({
  selector: 'app-candidate-job-info',
  templateUrl: './candidate-job-info.component.html',
  styleUrls: ['./candidate-job-info.component.scss']
})
export class CandidateJobInfoComponent implements OnInit {

  @Input() candidateJob: CandidateJob;
  @Input() isUserACandidate = true;
  @Input() isFromInput = false;
  @Input() inputUserId: string;
  solutionForm: FormGroup;
  links = [];

  userId: number;
  user: User;
  showMessage = false;
  isAdmin = false;
  submitted = false;

  constructor(private userService: UserService, private candidateService: CandidateService, private route: ActivatedRoute, private credentialService: CredentialsService) {
  }

  ngOnInit(): void {
    this.loadUser();
    this.checkRole();
    this.solutionForm = new FormGroup({
      solutions: new FormArray([
        new FormControl(null, Validators.required)
      ])
    });
  }

  loadUser(): void {
    this.userId = Number(this.inputUserId ? this.inputUserId : this.route.snapshot.params.id);
    this.userService.getUserById(this.userId).subscribe(data => {
      this.user = data;
      this.retrieveCandidateJobDetails(this.user.id);
      console.log(data);
    }, error => console.log(error));

    // this.showResume = true;
  }

  retrieveCandidateJobDetails(userId: number): void {
    this.candidateService.getCandidateCurrentJobByUserId(userId)
      .subscribe((candidateJob: CandidateJob) => {
        this.candidateJob = candidateJob;
        console.log(candidateJob);
      }, error => {
        console.log(error);
      });
  }

  onSubmit(): void {
    this.submitted = true;
    this.candidateJob.githubLinks = [];
    this.solutionForm.value.solutions.forEach(solution => {
      this.candidateJob.githubLinks.push(solution);
    });
    const req = {
      solutions: this.solutionForm.value,
      candidateJobId: this.candidateJob.id
    };
    this.candidateService.submitAssignmentSolution(req).subscribe(response => {
      this.showMessage = true;
      setTimeout(() => {
        this.showMessage = false;
      }, 1500);
    });
  }

  getSolutionControls() {
    return (this.solutionForm.get('solutions') as FormArray).controls;
  }

  addSolution() {
    const control = new FormControl(null, Validators.required);
    return (this.solutionForm.get('solutions') as FormArray).push(control);
  }

  deleteSolution(i: number): void {
    (this.solutionForm.get('solutions') as FormArray).controls.splice(i, 1);
  }

  checkRole(): void {
    this.isAdmin = this.credentialService.getCredentials().userEntity.roles.includes(UserRole.ROLE_ADMIN);
  }

  getStatus(applicationStatus: ApplicationStatus) {
    switch (applicationStatus) {
      case ApplicationStatus.INTERVIEW_SCHEDULED:
        return 'badge bg-secondary';
      case ApplicationStatus.SELECTED:
        return 'badge bg-success';
      case ApplicationStatus.PENDING:
        return 'badge bg-success';
      case ApplicationStatus.SUBMITTED:
        return 'badge bg-primary';
      case ApplicationStatus.NOT_ACCEPTED:
        return 'badge bg-danger';
    }
  }
}
