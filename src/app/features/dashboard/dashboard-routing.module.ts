import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashMainComponent} from './dash-main/dash-main.component';
import {DashboardWrapperComponent} from './dashboard-wrapper.component';
import {UserSettingComponent} from './user-setting/user-setting.component';
import {UserDetailsWrapperComponent} from '@app/features/dashboard/admin/user-details-wrapper/user-details-wrapper.component';
import {UserDetailsComponent} from '@app/features/dashboard/admin/user-details-wrapper/user-details/user-details.component';
import {AdminAuthenticationGuardGuard} from '@app/auth/guards/admin-authentication-guard.guard';
import {ProcessCandidateJobComponent} from '@app/features/dashboard/admin/process-candidate-job/process-candidate-job.component';
import {CandidateJobInfoComponent} from '@app/features/dashboard/components/candidate-job-info/candidate-job-info.component';
import {JobsPostingComponent} from '@app/features/dashboard/admin/job-wrapper/jobs-posting/jobs-posting.component';
import {JobWrapperComponent} from '@app/features/dashboard/admin/job-wrapper/job-wrapper.component';
import {JobListingComponent} from '@app/features/dashboard/admin/job-wrapper/job-listing/job-listing.component';
import {JobsUpdateComponent} from '@app/features/dashboard/admin/job-wrapper/jobs-update/jobs-update.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardWrapperComponent,
    children: [
      {
        path: '',
        component: DashMainComponent,
      },
      {
        path: 'settings',
        component: UserSettingComponent,
      },

      {
        path: 'user-details',
        component: UserDetailsWrapperComponent,
        children: [
          {path: '', component: UserDetailsComponent},
          {path: ':id', component: CandidateJobInfoComponent},
          {
            path: 'update/:id',
            component: ProcessCandidateJobComponent
          },
        ],
        canActivate: [AdminAuthenticationGuardGuard]
      },

      {
        path: 'job-posting',
        component: JobWrapperComponent,
        children: [
          {path: '', component: JobListingComponent},
          {path: 'new', component: JobsPostingComponent},
          {path: 'update/:id', component: JobsUpdateComponent}
        ]
      }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {
}
