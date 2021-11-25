import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginPageComponent} from './auth/login-page/login-page.component';
import {VerifyRequestComponent} from './auth/verify-request/verify-request.component';

const routes: Routes = [

  {
    path: 'login',
    component: LoginPageComponent,
  },
  {
    path: 'verify',
    component: VerifyRequestComponent,
  },

  {
    path: '',
    loadChildren: () => import('@app/features/features.module')
      .then(m => m.FeaturesModule),
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    initialNavigation: 'enabled'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
