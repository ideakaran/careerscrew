import {Component, OnInit} from '@angular/core';
import {Credentials, CredentialsService} from '@app/auth/services/credentials.service';
import {UserRole} from '@app/core/model/core.model';

@Component({
  selector: 'app-dash-main',
  templateUrl: './dash-main.component.html',
  styleUrls: ['./dash-main.component.scss']
})
export class DashMainComponent implements OnInit {

  isAdmin = false;

  constructor(private credentialService: CredentialsService) {
  }

  ngOnInit(): void {
    const credentials: Credentials = this.credentialService.getCredentials();
    const roles: Array<UserRole> = credentials.userEntity.roles;
    if (roles.includes(UserRole.ROLE_ADMIN) || roles.includes(UserRole.ROLE_MANAGER)) {
      this.isAdmin = true;
    } else {
      this.isAdmin = false;
    }
  }

}
