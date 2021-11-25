import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {ApplicationStatus, Interviewer} from '@app/core/model/core.model';
import { CandidateService } from '@app/core/services/candidate.service';
import { Column } from '@app/shared/simple-table/simple-table.model';
import { CandidateInterviewColumn } from '../../admin/user-details-wrapper/user-details/user-details.model';

@Component({
  selector: 'app-schedule-interview',
  templateUrl: './schedule-interview.component.html',
  styleUrls: ['./schedule-interview.component.scss']
})
export class ScheduleInterviewComponent implements OnInit {

  @Input() userName: string;
  @Input() candidateJobId: number;
  @Input() interviewers: Interviewer[];
  @Output() interviewScheduleValue = new EventEmitter<string>();
  loginForm: FormGroup;


  status: typeof ApplicationStatus;
  showDate = false;
  selectedOption: string;
  selectedInterviewers: string[];
  candidateInterviews = [];
  CandidateInterviewColumns: Column[] = CandidateInterviewColumn;

  constructor(private candidateService: CandidateService) {}


  ngOnInit(): void {
    this.status = ApplicationStatus;

    this.loginForm = new FormGroup({
      id: new FormControl(),
      interviewDate: new FormControl(),
      time: new FormControl(),
      interviewers: new FormControl(),
      description: new FormControl()
    });

    this.candidateService.getCandidateCurrentInterview(this.candidateJobId).subscribe(res => {
      if (res){
        const currentInterview = {
          id: res.id,
          interviewDate: res.interviewDate?.substring(0, 10),
          time: res?.interviewDate?.substring(11, 16),
          interviewersId: res?.interviewersId,
          interviewers: this.getInterviewerName(res?.interviewersId),
          description: res?.description
        };
        this.candidateInterviews.push(currentInterview);
      }
    },
    err => {
      console.log(err?.error?.response);
    });

  }

  getInterviewerName(id: Array<number>): Array<string> {
   return this.interviewers.filter(interviewer => id.includes(interviewer.id)).map(interviewer => interviewer.name);
  }

  onMetricChange(): void {
    // console.log('Selected are: ', this.selectedInterviewers);
  }

  onClick(): void {
    this.showDate = !this.showDate;
  }

  onClear(): void {
    this.loginForm.reset();
    this.selectedInterviewers = [];
  }

  updateInterview(data) :void{
        this.loginForm.setValue({
                  id: data.id,
                  interviewDate: data?.interviewDate,
                  time: data?.time,
                  interviewers: data?.interviewersId,
                  description: data?.description
                });
        this.showDate = !!data?.interviewDate;
        this.selectedInterviewers = this.loginForm.get('interviewers').value;
  }

  onSubmit(): void {
    this.loginForm.patchValue({interviewers: this.selectedInterviewers});
    this.interviewScheduleValue.emit(this.loginForm.value);
  }
}



