import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '@app/auth/services/authentication.service';
import {APP_PROPERTIES} from '@app/core/core.constant';
import {CredentialsService} from '@app/auth/services/credentials.service';
import {UserService} from '@app/core/services/user.service';

@Component({
  selector: 'app-home-navbar',
  templateUrl: './home-navbar.component.html',
  styleUrls: ['./home-navbar.component.scss']
})
export class HomeNavbarComponent implements OnInit {

  readonly appBrandName = APP_PROPERTIES.appBrandName;

  isNavCollapse = true;
  isLoggedIn = false;
  fullName: string | undefined;

  constructor(private credentialsService: CredentialsService,
              private authenticationService: AuthenticationService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.initializeLoggedInDetails();
  }

  private initializeLoggedInDetails(): void {
    this.isLoggedIn = this.credentialsService.isAuthenticated();
    if (this.isLoggedIn) {
      const credentials = this.credentialsService.getCredentials();
      this.fullName = credentials?.fullName;
    }
  }

  toggleNavbarClass(): void {
    this.isNavCollapse = !this.isNavCollapse;
  }

  logout(): void {
    this.authenticationService.logout();
  }

}
