import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {AuthenticationService} from '../services/authentication.service';
import {APP_ROUTES, QueryParamUIKey} from '@app/core/core.constant';
import {Router} from '@angular/router';
import {CredentialsService} from '@app/auth/services/credentials.service';

@Injectable()
export class HttpAuthInterceptor implements HttpInterceptor {

  constructor(private router: Router,
              private credentialsService: CredentialsService,
              private authenticationService: AuthenticationService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const clonedRequest = request.clone(
      {withCredentials: true}
    );

    return next.handle(clonedRequest)
      .pipe(
        map((event: HttpEvent<any>) => {
          if (event instanceof HttpResponse) {
            console.log('Http Response event... ' + event.status);
          }
          return event;
        }),
        catchError(error => {
          console.log('Error response status: ', error.status);
          const indexOfQueryParams = this.router.url.lastIndexOf('?');
          const lastIndexOfUrl = indexOfQueryParams > 0 ? indexOfQueryParams : this.router.url.length;
          const urlPath = this.router.url.substr(0, lastIndexOfUrl);
          const originalRequestedUri = urlPath && urlPath.length > 0 && urlPath ? urlPath : APP_ROUTES.HOME;

          // Error Status 401: UnAuthorized Access
          if (error.status === 401) {
            const wasAuthenticated = this.credentialsService.isAuthenticated();
            const UnAuthorizedAccessMsg = wasAuthenticated ? 'Session Timeout, Invalid Session ID !!!' : 'Un-Authorized Access ! Please Login !!!';
            this.authenticationService.logout(false);
            this.router.navigate([APP_ROUTES.LOGIN],
              {
                queryParams: {
                  [QueryParamUIKey.ORIGINAL_REQUEST_URI]: originalRequestedUri,
                  [QueryParamUIKey.DEFAULT_INFO_MESSAGE]: UnAuthorizedAccessMsg
                },
                replaceUrl: true
              });
          }
          // Error Status 403: Forbidden Access
          if (error.status === 403) {
            this.router.navigate([APP_ROUTES.LOGIN],
              {
                queryParams: {
                  [QueryParamUIKey.ORIGINAL_REQUEST_URI]: originalRequestedUri,
                  [QueryParamUIKey.DEFAULT_INFO_MESSAGE]: 'Forbidden, Please login with elevated credentials'
                },
                replaceUrl: true
              });
          }

          return throwError(error);
        }));
  }
}
