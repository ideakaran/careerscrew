import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardWrapperComponent} from './dashboard-wrapper.component';
import {DashMainComponent} from './dash-main/dash-main.component';
import {UserSettingComponent} from './user-setting/user-setting.component';
import {DashboardRoutingModule} from './dashboard-routing.module';
import {PublicPagesModule} from '../public-pages/public-pages.module';
import {UserDetailsComponent} from '@app/features/dashboard/admin/user-details-wrapper/user-details/user-details.component';
import {UpdateUserComponent} from '@app/features/dashboard/components/update-user/update-user.component';
import {UserInfoComponent} from '@app/features/dashboard/components/user-info/user-info.component';
import {UserDetailsWrapperComponent} from '@app/features/dashboard/admin/user-details-wrapper/user-details-wrapper.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CandidateJobInfoComponent} from './components/candidate-job-info/candidate-job-info.component';
import {ProcessCandidateJobComponent} from '@app/features/dashboard/admin/process-candidate-job/process-candidate-job.component';
import {PdfViewerModule} from 'ng2-pdf-viewer';
import {InitialAssessmentComponent} from './components/initial-assessment/initial-assessment.component';
import {ScheduleInterviewComponent} from './components/schedule-interview/schedule-interview.component';
import {FinalReviewComponent} from './components/final-review/final-review.component';
import {NgSelectModule} from '@ng-select/ng-select';

import {UserDashMainComponent} from './user/user-dash-main/user-dash-main.component';
import {AdminDashMainComponent} from './admin/admin-dash-main/admin-dash-main.component';
import {FileViewerComponent} from './components/file-viewer/file-viewer.component';
import {SharedModule} from '@app/shared/shared.module';
import { JobsUpdateComponent } from './admin/job-wrapper/jobs-update/jobs-update.component';
import {JobsPostingComponent} from '@app/features/dashboard/admin/job-wrapper/jobs-posting/jobs-posting.component';
import {JobListingComponent} from '@app/features/dashboard/admin/job-wrapper/job-listing/job-listing.component';
import {JobWrapperComponent} from '@app/features/dashboard/admin/job-wrapper/job-wrapper.component';
import {QuillModule} from "ngx-quill";
import { SidebarComponent } from './components/sidebar/sidebar.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { MessageSendReplyComponent } from './components/message-send-reply/message-send-reply.component';


@NgModule({
  declarations: [
    DashboardWrapperComponent,
    DashMainComponent,
    UserSettingComponent,

    UserDetailsComponent,
    UpdateUserComponent,
    UserInfoComponent,
    UserDetailsWrapperComponent,
    CandidateJobInfoComponent,
    ProcessCandidateJobComponent,
    InitialAssessmentComponent,
    ScheduleInterviewComponent,
    FinalReviewComponent,
    UserDashMainComponent,
    AdminDashMainComponent,
    FileViewerComponent,
    JobsPostingComponent,
    JobListingComponent,
    JobsUpdateComponent,
    JobWrapperComponent,
    SidebarComponent,
    MessageSendReplyComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,

    PublicPagesModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    PdfViewerModule,
    NgSelectModule,
    SharedModule,
    QuillModule
  ]
})
export class DashboardModule {
}
