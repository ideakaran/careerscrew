import { Component, OnInit } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-initial-assessment',
  templateUrl: './initial-assessment.component.html',
  styleUrls: ['./initial-assessment.component.scss']
})
export class InitialAssessmentComponent implements OnInit {
  @Output() initialAssessmentValue = new EventEmitter<string>();
  assessmentForm: FormGroup;
  assessmentEditorStyle = {
    height: '150px',
  };
  constructor() { }

  ngOnInit(): void {
    this.assessmentForm = new FormGroup({
      description: new FormControl(),
    });
  }
  onSubmit() {
    this.initialAssessmentValue.emit(this.assessmentForm.value);
  }

}
