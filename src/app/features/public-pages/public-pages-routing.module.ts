import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PublicPagesWrapperComponent } from './public-pages-wrapper.component';
import { HomePageComponent } from './home-page/home-page.component';
import { FaqPageComponent } from './faq-page/faq-page.component';
import {JobListingComponent} from '@app/features/public-pages/job-listing/job-listing.component';
import {CareersApplyComponent} from '@app/features/public-pages/components/careers-apply/careers-apply.component';


const routes: Routes = [
  {
    path: '',
    component: PublicPagesWrapperComponent,
    children: [
      {
        path: '',
        component: JobListingComponent,
         pathMatch: 'full',
        data: {
          title: 'Home'
        },
      },
      {
        path: 'apply-job/:id',
        component: CareersApplyComponent,
        pathMatch: 'full',
        data: {
          title: 'Home'
        },
      },
      {
        path: 'faq',
        component: FaqPageComponent,
        data: {
          title: 'FAQ'
        }
      },

    ]

  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicPagesRoutingModule { }
