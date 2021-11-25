import {Component, OnInit} from '@angular/core';
import {JobsService} from '@app/core/services/jobs.service';
import {Job} from '@app/core/model/core.model';
import {CareersApplyService} from '@app/features/public-pages/components/careers-apply/careers-apply.service';

@Component({
  selector: 'app-job-listing',
  templateUrl: './job-listing.component.html',
  styleUrls: ['./job-listing.component.scss']
})
export class JobListingComponent implements OnInit {
  jobs: Job[];

  constructor(private careersApplyService: CareersApplyService) {
  }

  activeIds: string[] = [];

  ngOnInit(): void {
    this.careersApplyService.getAllJob().subscribe(response => {
      this.jobs = response.object;
      this.jobs.forEach(job => {
        this.activeIds.push('ac' + job.id.toString());
      });
    });
  }

}
