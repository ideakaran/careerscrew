import {Component, OnInit} from '@angular/core';
import {APP_ROUTES} from '@app/core/core.constant';
import {AuthenticationService} from '@app/auth/services/authentication.service';
import {CredentialsService} from '@app/auth/services/credentials.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  readonly APP_ROUTES = APP_ROUTES;

  fullName: string | undefined;


  constructor(private credentialsService: CredentialsService, private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    const credentials = this.credentialsService.getCredentials();
    this.fullName = credentials?.fullName;
  }

  logout(): void {
    this.authenticationService.logout();
  }

  hasAccessToViewRoute(appRoute: APP_ROUTES): boolean {
    return this.authenticationService.hasAccessToViewRoute(appRoute);
  }


}
