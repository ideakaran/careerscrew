import {Component, OnInit} from '@angular/core';
import {Job} from '@app/core/model/core.model';
import {UserService} from '@app/core/services/user.service';

@Component({
  selector: 'app-job-wrapper',
  templateUrl: './job-wrapper.component.html',
  styleUrls: ['./job-wrapper.component.scss']
})
export class JobWrapperComponent implements OnInit {
  jobs: Job[];

  constructor() { }

  ngOnInit(): void {
  }
}
