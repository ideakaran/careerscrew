import {Component, OnInit} from '@angular/core';
import {Job} from '@app/core/model/core.model';
import {Router} from '@angular/router';
import {JobsService} from '@app/core/services/jobs.service';
import {APP_ROUTES} from '@app/core/core.constant';
import {JobColumn} from '@app/features/dashboard/admin/job-wrapper/job-listing/job-listing.model';

@Component({
  selector: 'app-job-listing',
  templateUrl: './job-listing.component.html',
  styleUrls: ['./job-listing.component.scss']
})
export class JobListingComponent implements OnInit {

  readonly APP_ROUTES = APP_ROUTES;
  readonly JobColumn = JobColumn;

  jobs: Job[];

  constructor(private jobService: JobsService, private router: Router) {
  }

  ngOnInit(): void {
    this.jobService.getAllJobs().subscribe((jobs: Job[]) => {
      this.jobs = jobs;
    });
  }

  updateJob(data: Job): void {
    this.router.navigate([APP_ROUTES.DASH_JOB_DETAILS_UPDATE, data.id]);
  }
}
