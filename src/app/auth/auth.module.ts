import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginPageComponent} from './login-page/login-page.component';
import {VerifyRequestComponent} from './verify-request/verify-request.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '@app/shared/shared.module';
import {RouterModule} from '@angular/router';
import {HttpAuthInterceptor} from './interceptors/http-auth.interceptor';


@NgModule({
  declarations: [
    LoginPageComponent,
    VerifyRequestComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([]),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    SharedModule,
  ],
  providers: [
    {
        provide: HTTP_INTERCEPTORS,
        useClass: HttpAuthInterceptor,
        multi: true
    },
  ]
})
export class AuthModule { }
