import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoadingComponent} from './loading/loading.component';
import {SimpleTableComponent} from './simple-table/simple-table.component';
import {StColumnSortDirective} from './simple-table/st-column-sort/st-column-sort.directive';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {ObjKeysPipe} from './simple-table/pipe/obj-keys.pipe';
import {StPaginationComponent} from './simple-table/st-pagination/st-pagination.component';
import { DashboardBoxComponent } from './dashboard-box/dashboard-box.component';

const sharedComponents = [
  ObjKeysPipe,
  LoadingComponent,
  SimpleTableComponent,
];

@NgModule({
  declarations: [
    ...sharedComponents,
    StColumnSortDirective,
    StPaginationComponent,
    DashboardBoxComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  exports: [...sharedComponents, DashboardBoxComponent]
})
export class SharedModule {
}
