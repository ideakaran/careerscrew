import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Credentials, CredentialsService} from '@app/auth/services/credentials.service';
import {KpiValues, ResponseModel, UserRole} from '@app/core/model/core.model';
import {APP_ROUTES, QueryParamUIKey} from '@app/core/core.constant';
import { HttpClient } from '@angular/common/http';
import { ApiEndpoints } from '@app/core/app-url.constant';
import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-admin-dash-main',
  templateUrl: './admin-dash-main.component.html',
  styleUrls: ['./admin-dash-main.component.scss']
})
export class AdminDashMainComponent implements OnInit, OnDestroy {

  userId: number;
  kpiValues: KpiValues;
  private unsubscribe = new Subject<void>();

  constructor(private router: Router, private credentialService: CredentialsService, private http: HttpClient) {
  }

  ngOnInit(): void {
    const credentials: Credentials = this.credentialService.getCredentials();
    const roles: Array<UserRole> = credentials.userEntity.roles;
    this.userId = credentials.userEntity?.id;

    const $observableVal = this.http.get(ApiEndpoints.DASHBOARD_BOX.GET_KPI_VALUES).pipe(
      map((response: ResponseModel<KpiValues>) => {
        return response.object;
      })
    );
    $observableVal.pipe(takeUntil(this.unsubscribe))
        .subscribe((response) => {
        this.kpiValues = response;
      });



    // For now redirecting to user-details page as default dash-page
    // this.router.navigate([APP_ROUTES.DASH_USER_DETAILS], {
    //   queryParams: {[QueryParamUIKey.REFERRED_FROM_URI]: APP_ROUTES.DASHBOARD},
    //   replaceUrl: true
    // });
  }


  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

}
