import {AbstractControlOptions, ValidatorFn, Validators} from "@angular/forms";

export interface GenericResponse<T> {
  response: T;
  messageCode: string;
}


export interface FormControlValidators {
  controlName: string;
  validators: ValidatorFn | ValidatorFn[] | null;
}
