import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Experience, Job} from '@app/core/model/core.model';
import {JobsService} from '@app/core/services/jobs.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {APP_ROUTES} from '@app/core/core.constant';

@Component({
  selector: 'app-jobs-update',
  templateUrl: './jobs-update.component.html',
  styleUrls: ['./jobs-update.component.scss']
})
export class JobsUpdateComponent implements OnInit {
  jobId: number;
  job: Job;
  alert = false;
  keys = [];
  loginForm = new FormGroup({
    title: new FormControl(),
    position: new FormControl(),
    description: new FormControl(),
    isActive: new FormControl()
  });

  readonly APP_ROUTES = APP_ROUTES;

  constructor(private route: ActivatedRoute,
              private jobService: JobsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.keys = (Object.values(Experience) as string[]);
    this.jobId = this.route.snapshot.params.id;
    this.jobService.getJobById(this.jobId).subscribe(job => {
      this.job = job;
      this.initializeJobUpdateForm();
      }, err => console.log(err));
  }

  initializeJobUpdateForm(): void {
    this.loginForm = new FormGroup({
      id: new FormControl(this.job['id']),
      title: new FormControl(this.job['title']),
      position: new FormControl(this.job['position']),
      description: new FormControl(this.job['description']),
      isActive: new FormControl(this.job['isActive']),
    });
  }

  onSubmit(): void {
    this.jobService.updateJob(this.job.id, this.loginForm.value).subscribe(() => {
      this.alert = true;
      this.loginForm.reset();
      setTimeout(() => {
        this.alert = false;
      }, 3000);
    });
  }
}
