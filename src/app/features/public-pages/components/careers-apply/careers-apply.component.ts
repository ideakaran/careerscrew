import {DatePipe} from '@angular/common';
import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CareersApplyService} from './careers-apply.service';
import {Job, JobApplyRequest} from '@app/core/model/core.model';
import {CoreUtil} from '@app/core/core.util';
import {FormControlValidators} from '@app/core/model/core-ui.model';
import {APP_PROPERTIES} from '@app/core/core.constant';
import {ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-careers-apply',
  templateUrl: './careers-apply.component.html',
  styleUrls: ['./careers-apply.component.scss']
})
export class CareersApplyComponent implements OnInit {

  readonly maxResumeFileSize = APP_PROPERTIES.maxResumeFileSize;
  readonly allowedFileTypes = APP_PROPERTIES.allowedFileTypes;
  readonly referrerEmailPattern = APP_PROPERTIES.referrerEmailPattern;

  isFileSizeError: boolean;
  isFileFormatError: boolean;

  jobs: Job[];

  loading: boolean;
  hasError: boolean;
  jobApplyResponseMessage: string;

  isSubmitted: boolean;
  jobApplyForm!: FormGroup;

  @ViewChild('resumeFileInputElement')
  resumeFileInputElement: ElementRef;
  jobId: number;

  constructor(private careersApplyService: CareersApplyService,
              private formBuilder: FormBuilder,
              private datePipe: DatePipe,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.fetchJobs();
    this.initializeLoginForm();
    this.setFormValueFromParam();
  }
  setFormValueFromParam(): void{
    this.route.params.subscribe((params: Params) => {
      this.jobId = +params.id;
      if (this.jobId) {
        this.jobApplyForm.patchValue({
          jobId: this.jobId,
        });
      }
    });
  }

  fetchJobs(): void {
    this.careersApplyService.getAllJob().subscribe(res => {
      this.jobs = res?.object;
    }, err => {
      console.log(err);
    });
  }

  initializeLoginForm(): void {
    const optionalFormControlValidations: Array<FormControlValidators> = [
      {
        controlName: 'refererFullName',
        validators: Validators.required
      },
      {
        controlName: 'refererEmail',
        validators: [Validators.required, this.referrerEmailPattern ? Validators.pattern(this.referrerEmailPattern) : Validators.email]
      }
    ];
    // initializing JobApply Form
    this.jobApplyForm = this.formBuilder.group({
      fullName: ['', [Validators.required]],
      email: ['', [Validators.email]],
      contactNumber: ['', [Validators.required, Validators.maxLength(10)]],
      isRefererChecked: [false],
      refererFullName: [''],
      refererEmail: [''],
      jobId: [this.jobId],
      resumeFile: [null, [Validators.required]]
    }, {
      validator: CoreUtil.setValidatorsForConsecutiveFieldsOnlyIfRefControlIsTrue('isRefererChecked', optionalFormControlValidations),
    });
  }

  onChange(event): void {
    const file: File = (event.target as HTMLInputElement).files[0];
    this.jobApplyForm.patchValue({
      resumeFile: file
    });
    if (file) {
      if (this.maxResumeFileSize != null && this.maxResumeFileSize > 0) {
        const size = file.size / 1024 / 1024;
        this.isFileSizeError = (size > this.maxResumeFileSize) ? true : false;
      }
      if (this.allowedFileTypes != null) {
        const fileExtension = file.name.split('.')[1];
        this.isFileFormatError = !this.allowedFileTypes.includes('.' + fileExtension);
      }
    }
    this.jobApplyForm.get('resumeFile').updateValueAndValidity();
  }

  get formControls(): { [p: string]: AbstractControl } {
    return this.jobApplyForm.controls;
  }

  onJobApply(): void {
    console.log('form ', this.jobApplyForm);
    this.isSubmitted = true;
    if (this.jobApplyForm.invalid || this.isFileSizeError || this.isFileFormatError) {
      return;
    }
    const formValue = this.jobApplyForm.value;
    const jobApplyRequest: JobApplyRequest = {
      fullName: formValue.fullName,
      email: formValue.email,
      contactNumber: formValue.contactNumber,
      jobId: formValue.jobId,
      refererFullName: formValue.refererFullName,
      refererEmail: formValue.refererEmail,
    };
    this.loading = true;
    this.careersApplyService.applyForJob(jobApplyRequest, formValue.resumeFile)
      .subscribe(
        res => {
          this.loading = false;
          this.hasError = false;
          this.jobApplyResponseMessage = 'Application submitted successfully, Please check your email';
          this.isSubmitted = false;
          this.jobApplyForm.reset();
          this.resumeFileInputElement.nativeElement.value = '';
        },
        err => {
          this.loading = false;
          this.hasError = true;
          console.log(err);
          this.jobApplyResponseMessage = err?.error?.response || 'Sorry! Something went wrong !!!';
        }
      );
  }

  isSelected(): boolean {
    return true;
  }
}
