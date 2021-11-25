import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ApplicationStatus } from '@app/core/model/core.model';
import { CandidateService } from '@app/core/services/candidate.service';

@Component({
  selector: 'app-final-review',
  templateUrl: './final-review.component.html',
  styleUrls: ['./final-review.component.scss']
})
export class FinalReviewComponent implements OnInit {
  @Input() userName: string;
  @Input() candidateJobId: number;
  @Output() finalReviewValue = new EventEmitter<string>();
  finalReviewForm: FormGroup;

  status: typeof ApplicationStatus;
  showDate = false;
  selectedOption: string;

  constructor(private candidateService: CandidateService) {
  }

  ngOnInit(): void {

    this.status = ApplicationStatus;
    this.finalReviewForm = new FormGroup({
      id: new FormControl(),
      tackling: new FormControl(),
      communication: new FormControl(),
      stability: new FormControl(),
      grace: new FormControl(),
      writtenTestScore: new FormControl(),
      isSuitableForHiring: new FormControl(),
      personality: new FormControl(),
      knowledge: new FormControl(),
      training: new FormControl(),
      experience: new FormControl(),
      remarks: new FormControl()
    });

    this.candidateService.getCandidateCurrentRemark(this.candidateJobId).subscribe(res => {
      if (res) {
        this.finalReviewForm.setValue({
          id: res?.id,
          knowledge: res?.knowledge,
          experience: '',
          training: res?.tackling,
          communication: res?.communication,
          tackling: res?.tackling,
          writtenTestScore: res?.writtenTestScore,
          stability: res?.stability,
          grace: res?.grace,
          isSuitableForHiring: res?.isSuitableForHiring,
          personality: res?.personality,
          remarks: res?.remarks
        });
      }
    },
    err => {
      console.log(err?.error?.response);
    });

  }


  onSave(): void {
    this.finalReviewValue.emit(this.finalReviewForm.value);
  }
}
