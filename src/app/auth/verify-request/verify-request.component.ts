import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, convertToParamMap, ParamMap, Params} from '@angular/router';
import {APP_PROPERTIES, QueryParamKey} from '@app/core/core.constant';
import {CoreUtil} from '@app/core/core.util';
import {Observable, Subject} from 'rxjs';
import {filter, takeUntil} from 'rxjs/operators';
import {VerifyForgotPasswordUpdateContext} from '../auth.model';
import {AuthenticationService} from '../services/authentication.service';
import {ResponseModel} from '@app/core/model/core.model';
import {HttpClient} from '@angular/common/http';
import {ApiEndpoints} from "@app/core/app-url.constant";

@Component({
  selector: 'app-verify-request',
  templateUrl: './verify-request.component.html',
  styleUrls: ['./verify-request.component.scss']
})
export class VerifyRequestComponent implements OnInit {

  readonly appBrandName = APP_PROPERTIES.appBrandName;

  // Page State
  loading = false;
  hasError = false;
  responseMessage = '';

  // Form state
  setNewPasswordForm!: FormGroup;
  isRPFormSubmitted = false;

  isRequestHitApiEndpoint = false;
  isProcessResetPassword = false;
  routeQueryParams: Params = {};
  paramMap!: ParamMap;

  private unsubscribe = new Subject<void>();

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private http: HttpClient) {
  }

  ngOnInit(): void {
    this.processRouteQueryParams();
    this.initializeSetNewPasswordForm();
  }

  initializeSetNewPasswordForm(): void {
    this.setNewPasswordForm = this.formBuilder.group({
      password: ['', [Validators.required]],
      passwordConfirm: ['', Validators.required]
    }, {
      validator: CoreUtil.verifyAreFieldsMatchingValidator('password', 'passwordConfirm')
    });
  }

  get formControls(): { [p: string]: AbstractControl } {
    return this.setNewPasswordForm.controls;
  }

  private processRouteQueryParams(): void {
    this.route.queryParams
      .pipe(
        filter((val: Params) => val && Object.keys(val).length > 0),
        takeUntil(this.unsubscribe)
      )
      .subscribe((params: Params) => {
        this.routeQueryParams = params;
        const paramMap: ParamMap = convertToParamMap(params);
        this.paramMap = paramMap;
        const apiEndpointToHit = paramMap.get(QueryParamKey.API_ENDPOINT_TO_HIT) || '';
        const isProcessPasswordReset = paramMap.get(QueryParamKey.IS_PROCESS_PASSWORD_RESET) || '';
        if (apiEndpointToHit && apiEndpointToHit.length > 0) {
          this.isRequestHitApiEndpoint = true;
          this.hitApiEndpointAndShowResponseStatus(this.paramMap);
        } else if (isProcessPasswordReset && isProcessPasswordReset.length > 0) {
          this.initializeSetNewPasswordForm();
          this.isProcessResetPassword = true;
        } else {
          this.isRequestHitApiEndpoint = false;
          this.isProcessResetPassword = false;
        }

      });
  }

  private hitApiEndpointAndShowResponseStatus(paramMap: ParamMap): void {
    console.log('hitApiEndpointAndShowResponseStatus - verification request', paramMap);
    const apiEndpointToHit = paramMap.get(QueryParamKey.API_ENDPOINT_TO_HIT) || '';
    const apiHitResponse: Observable<ResponseModel<string>> = this.http.get<ResponseModel<string>>(ApiEndpoints.API_URL + apiEndpointToHit);
    apiHitResponse.subscribe(
      res => {
        this.loading = false;
        this.hasError = false;
        this.responseMessage = res.message;
      },
      err => {
        this.loading = false;
        this.hasError = true;
        this.responseMessage = err?.error?.response || 'Sorry! Something went wrong !!!';
      }
    );

  }

  onNewPasswordSet(): void {
    this.isRPFormSubmitted = true;
    if (this.setNewPasswordForm.invalid) {
      return;
    }
    const formValue = this.setNewPasswordForm.value;
    this.verifyAndProcessForgotPasswordRequest(this.paramMap, formValue.password);
  }

  private verifyAndProcessForgotPasswordRequest(paramMap: ParamMap, newPassword: string): void {
    console.log('verify email - reset password request', paramMap);
    const email = paramMap.get(QueryParamKey.EMAIL) || '';
    const forgotPasswordVerCode = paramMap.get(QueryParamKey.FORGOT_PASSWORD_VER_CODE) || '';
    const verifyForgotPasswordUpdateContext: VerifyForgotPasswordUpdateContext = {
      email: email,
      forgotPasswordVerCode: forgotPasswordVerCode,
      newPassword: newPassword
    };
    this.loading = true;
    this.authenticationService.verifyAndProcessPasswordUpdateRequest(verifyForgotPasswordUpdateContext, true)
      .pipe()
      .subscribe(
        res => {
          this.loading = false;
          this.hasError = false;
          this.responseMessage = 'Password reset Successful';
        },
        err => {
          this.loading = false;
          this.hasError = true;
          console.log(err);
          this.responseMessage = err?.error?.response || 'Sorry! Something went wrong !!!';
        }
      );
  }

}


