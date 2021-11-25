import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {APP_ROUTES, QueryParamUIKey} from '@app/core/core.constant';
import {Credentials, CredentialsService} from '@app/auth/services/credentials.service';
import {UserRole} from '@app/core/model/core.model';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthenticationGuardGuard implements CanActivate {

  constructor(private router: Router, private credentialsService: CredentialsService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const credentials: Credentials = this.credentialsService.getCredentials();
    const roles: Array<UserRole> = credentials.userEntity.roles;
    if (roles.includes(UserRole.ROLE_ADMIN) || roles.includes(UserRole.ROLE_MANAGER)) {
      return true;
    }
    console.log('No Admin Access, redirecting to login...');
    const LoginRoutePath = APP_ROUTES.LOGIN;
    this.router.navigate([LoginRoutePath], {
      queryParams: {
        [QueryParamUIKey.ORIGINAL_REQUEST_URI]: state.url,
        [QueryParamUIKey.DEFAULT_INFO_MESSAGE]: 'No Admin Access, Please login with elevated credentials...'
      },
      replaceUrl: true
    });
    return false;
  }

}
