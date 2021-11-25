import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { APP_ROUTES, QueryParamUIKey } from '@app/core/core.constant';
import { Observable } from 'rxjs';
import { CredentialsService } from '../services/credentials.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router, private credentialsService: CredentialsService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.credentialsService.isAuthenticated()) {
      return true;
    }

    console.log('Not authenticated, redirecting to login...');
    const LoginRoutePath = APP_ROUTES.LOGIN;
    // const subStringUrlOnly: string = this.router.url.substring(0, this.router.url.indexOf('?'));
    this.router.navigate([LoginRoutePath], {
      queryParams: {[QueryParamUIKey.ORIGINAL_REQUEST_URI]: state.url},
      replaceUrl: true
    });
    return false;
  }

}
