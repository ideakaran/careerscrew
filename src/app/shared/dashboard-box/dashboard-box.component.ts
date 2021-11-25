import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiEndpoints} from '@app/core/app-url.constant';
import {map, takeUntil} from 'rxjs/operators';
import {ResponseModel} from '@app/core/model/core.model';
import {Subject} from "rxjs";

@Component({
  selector: 'app-dashboard-box',
  templateUrl: './dashboard-box.component.html',
  styleUrls: ['./dashboard-box.component.scss']
})
export class DashboardBoxComponent implements OnInit, OnDestroy {

  URLEndpoint = ApiEndpoints.DASHBOARD_BOX;
  ENDPOINTMAP = new Map([
    ['recent_hire', this.URLEndpoint.GET_RECENT_HIRED],
    ['new_candidates', this.URLEndpoint.GET_NEW_CANDIDATES],
    ['active_vacancies', this.URLEndpoint.GET_ACTIVE_VACANCIES],
    ['active_referrals', this.URLEndpoint.GET_ACTIVE_REFERRALS],
  ]);


  @Input() identifier: string;
  @Input() label: string;
  @Input() count: number;
  @Input() styles: {icon, style};
  // count: number;
  data: object;

  private unsubscribe = new Subject<void>();

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    console.log("label: ", this.label, " count: ", this.count);
    
    // if (this.identifier) {
    //   const url = this.ENDPOINTMAP.get(this.identifier);
    //   const $observableVal = this.http.get(url).pipe(
    //     map((response: ResponseModel<DashboardBoxResponse>) => {
    //       return response.object;
    //     })
    //   );
    //   $observableVal.pipe(takeUntil(this.unsubscribe))
    //     .subscribe((response) => {
    //     if (!this.label) {
    //       this.label = response.label;
    //     }
    //     this.data = response.data;
    //     this.count = response.count;
    //   });
    // }
  }

  handleClick(): void {
    console.log('On Click Show charts and better visualization for that particular box');
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

}

// class DashboardBoxResponse {
//   label: string;
//   count: number;
//   data: object;
// }
