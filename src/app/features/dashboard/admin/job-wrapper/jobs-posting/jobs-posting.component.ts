import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Experience} from '@app/core/model/core.model';
import {JobsService} from '@app/core/services/jobs.service';
import {APP_ROUTES} from '@app/core/core.constant';

@Component({
  selector: 'app-jobs-wrapper',
  templateUrl: './jobs-posting.component.html',
  styleUrls: ['./jobs-posting.component.scss']
})
export class JobsPostingComponent implements OnInit {

  readonly APP_ROUTES = APP_ROUTES;
  loginForm: FormGroup;

  experience: typeof Experience;
  keys = [];
  jobAdded = false;
  responseMessage: string;
  alert = false;
  constructor(private jobService: JobsService) { }

  ngOnInit(): void {
    this.keys = (Object.values(Experience) as string[]);
    this.loginForm = new FormGroup({
      title: new FormControl(),
      position: new FormControl(),
      description: new FormControl(),
      isActive: new FormControl(true)
    });
  }


  onSubmit(): void {
    this.jobService.addJobs(this.loginForm.value).subscribe((res) => {
        this.jobAdded = true;
        this.responseMessage = 'Job has been added successfully';
        this.loginForm.reset();
        this.alert = true;
        setTimeout(() => {
          this.alert = false;
        }, 2000);
      }
    );
  }

}
