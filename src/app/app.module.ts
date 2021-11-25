import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';

import {AppRoutingModule} from './app-routing.module';
import {AuthModule} from '@app/auth/auth.module';
import {CoreModule} from '@app/core/core.module';
import {SharedModule} from '@app/shared/shared.module';
import {FeaturesModule} from '@app/features/features.module';
import {DatePipe} from '@angular/common';
import {QuillModule} from "ngx-quill";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    CoreModule,
    SharedModule,
    FeaturesModule,
    QuillModule.forRoot()
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
