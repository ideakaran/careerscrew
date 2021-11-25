import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PublicPagesWrapperComponent} from './public-pages-wrapper.component';
import {HomePageComponent} from './home-page/home-page.component';
import {HomeNavbarComponent} from './components/home-navbar/home-navbar.component';
import {PublicPagesRoutingModule} from './public-pages-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CareersApplyComponent} from './components/careers-apply/careers-apply.component';
import {FaqPageComponent} from './faq-page/faq-page.component';
import {SharedModule} from '@app/shared/shared.module';
import { JobListingComponent } from './job-listing/job-listing.component';


@NgModule({
  declarations: [
    PublicPagesWrapperComponent,
    HomeNavbarComponent,
    CareersApplyComponent,
    HomePageComponent,
    FaqPageComponent,
    JobListingComponent
  ],
  imports: [
    CommonModule,
    PublicPagesRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    SharedModule
  ],
  exports: [HomeNavbarComponent]
})
export class PublicPagesModule {
}
