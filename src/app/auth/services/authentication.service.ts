import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {map} from 'rxjs/operators';

import {ApiEndpoints} from '@app/core/app-url.constant';
import {User, UserRole} from '@app/core/model/core.model';
import {Observable} from 'rxjs';
import {LoginContext, VerifyForgotPasswordUpdateContext} from '../auth.model';
import {Credentials, CredentialsService} from './credentials.service';
import {APP_ROUTES, APP_ROUTES_AND_ACCESS, QueryParamUIKey} from '@app/core/core.constant';
import {GenericResponse} from '@app/core/model/core-ui.model';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  readonly AUTH_URL = ApiEndpoints.AUTH;

  constructor(private credentialsService: CredentialsService,
              private router: Router,
              private route: ActivatedRoute,
              private http: HttpClient) {
  }

  public login(loginContext: LoginContext, redirectAfterLogin: boolean = false, defaultRedirectUri: string | null = APP_ROUTES.HOME): Observable<Credentials> {
    const authResponseObservable: Observable<User> = this.http.post<User>(this.AUTH_URL.USER_LOGIN, loginContext);
    const generateCredentialsObservable: Observable<Credentials> = authResponseObservable.pipe(
      map((userEntity: User) => {
        const credentialsData: Credentials = {
          email: userEntity.email,
          fullName: userEntity.fullName,
          userEntity: userEntity
        };
        this.credentialsService.setCredentials(credentialsData, loginContext.rememberMe);
        console.log('User Login successful generated credentials ', credentialsData);

        if (redirectAfterLogin) {
          if (defaultRedirectUri && defaultRedirectUri.length > 0) {
            defaultRedirectUri = defaultRedirectUri;
          } else {
            defaultRedirectUri = APP_ROUTES.DASHBOARD;
          }
          const targetUri = defaultRedirectUri && defaultRedirectUri.length > 0 ? defaultRedirectUri : '/';
          this.router.navigateByUrl(targetUri, {state: {}});
          return;
        }
        return credentialsData;
      })
    );

    return generateCredentialsObservable;
  }

  public sendEmailForforgotPassword(email: string): Observable<GenericResponse<boolean>> {
    const forgotPasswordResponse: Observable<GenericResponse<boolean>> = this.http.post<GenericResponse<boolean>>(this.AUTH_URL.FORGOT_PASSWORD, {email: email});
    const processedForgotPasswordResponse: Observable<GenericResponse<boolean>> = forgotPasswordResponse.pipe(
      map((passwordForgotResponse: GenericResponse<boolean>): GenericResponse<boolean> => {
        console.log('Password reset link Sent for email: ', email);
        return passwordForgotResponse;
      })
    );
    return processedForgotPasswordResponse;
  }

  public verifyAndProcessPasswordUpdateRequest(verifyForgotPasswordContext: VerifyForgotPasswordUpdateContext, redirectToLoginUri?: boolean): Observable<GenericResponse<boolean>> {
    const fpVerificationResponseObs: Observable<GenericResponse<boolean>> = this.http.post<GenericResponse<boolean>>(this.AUTH_URL.PASSWORD_RESET_SET_NEW_PASS, verifyForgotPasswordContext);
    const processedFPVerificationResponseObs: Observable<GenericResponse<boolean>> = fpVerificationResponseObs.pipe(
      map((fpVerificationResponse: GenericResponse<boolean>) => {
        if (redirectToLoginUri) {
          console.log('Password reset request verified and new password set: ', verifyForgotPasswordContext.email);
          this.router.navigate([APP_ROUTES.LOGIN], {
            queryParams: {[QueryParamUIKey.PASSWORD_RESET_SUCCESSFUL]: 'Password Reset Successful. Please Login'},
            replaceUrl: true
          });
        }
        return fpVerificationResponse;
      })
    );
    return processedFPVerificationResponseObs;
  }

  public logout(shouldRedirect: boolean = true): void {
    console.log('Logout called');
    this.http.get<void>(this.AUTH_URL.USER_LOGOUT)
      .subscribe(res => {
        this.credentialsService.setCredentials(null);
        console.log('Logout Successful, redirecting to Login');
        const appendParams = {[QueryParamUIKey.LOGOUT_SUCCESSFUL]: 'Logout Successful'};
        if (shouldRedirect) {
          this.router.navigate([APP_ROUTES.LOGIN], {queryParams: appendParams, replaceUrl: true});
        }
      });
  }

  hasAccessToViewRoute(appRouteName: APP_ROUTES): boolean {
    const appRouteAllowedRoles = APP_ROUTES_AND_ACCESS[appRouteName];
    const credentials: Credentials = this.credentialsService.getCredentials();
    const roles: Array<UserRole> = credentials.userEntity.roles;
    if (appRouteAllowedRoles.allowedRoles && appRouteAllowedRoles.allowedRoles.length <= 0 ) {
      return true;
    } else {
      const hasAccess = roles.some(userRole => appRouteAllowedRoles.allowedRoles.includes(userRole));
      return hasAccess;
    }
    return false;
  }

}
