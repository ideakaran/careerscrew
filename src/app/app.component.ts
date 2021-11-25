import { Component } from '@angular/core';
import { APP_PROPERTIES } from '@app/core/core.constant';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  readonly appBrandName = APP_PROPERTIES.appBrandName;

}
