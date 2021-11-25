import {FormGroup} from '@angular/forms';
import {FormControlValidators} from '@app/core/model/core-ui.model';
import {HttpParams} from '@angular/common/http';
import {PageRequest} from '@app/core/model/core.model';

export class CoreUtil {

  public static confirmedValidator = 'confirmedValidator';

  static buildPageParams = (pageRequest: PageRequest): HttpParams => {
    return  new HttpParams()
      .set('page', (pageRequest.page - 1).toString())
      .set('size', pageRequest.size.toString())
      .set('sort', pageRequest.sort.toString() + ',' + pageRequest.direction.toString());
  }

  static verifyAreFieldsMatchingValidator(controlName: string, matchingControlName: string): (formGroup: FormGroup) => any {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (matchingControl.errors && !matchingControl.errors.confirmedValidator) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({[this.confirmedValidator]: true});
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  static setValidatorsForConsecutiveFieldsOnlyIfRefControlIsTrue(
    booleanControlName: string,
    matchingControlWithValidators: Array<FormControlValidators>): (formGroup: FormGroup) => any {

    return (formGroup: FormGroup) => {
      const mainBooleanControl = formGroup.controls[booleanControlName];
      mainBooleanControl.valueChanges.subscribe((isTrue: boolean) => {
        for (const mc of matchingControlWithValidators) {
          const matchingControl = formGroup.controls[mc.controlName];
          if (isTrue) {
            matchingControl.setValidators(mc.validators);
          } else {
            matchingControl.clearValidators();
          }
          matchingControl.updateValueAndValidity({onlySelf: true, emitEvent: false});
        }
      });

    };
  }

}
