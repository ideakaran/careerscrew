import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobDashboardComponent } from './job-dashboard/job-dashboard.component';
import { UpdateComponent } from './update/update.component';

const routes: Routes = [
  {path : '', component : JobDashboardComponent},
  {path : 'update/:id', component : UpdateComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
