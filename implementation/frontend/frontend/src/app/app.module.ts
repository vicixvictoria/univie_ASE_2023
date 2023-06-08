import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatToolbarModule} from "@angular/material/toolbar";
import {AppRoutingModule} from './app-routing.module';
import { AppComponent } from './app.component';
import {HomeComponent} from "./home/home.component";
import {MatIconModule} from "@angular/material/icon";
import {EventInventoryComponent} from "./eventInventory/eventInventory.component";
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {SearchServiceComponent} from "./searchService/searchService.component";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CommonModule, Location} from "@angular/common";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

//import {NgbModalModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AlertComponent} from "./alert/alert.component";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {ErrorInterceptor} from "./interceptors/error.interceptor";
import {fakeBackendProvider} from "./interceptors/fake-backend";
import {AddEventComponent} from "./addEvents/addEvent.component";
import {CalendarComponent} from "./calendar/calendar.component";
import {UpdateEventComponent} from "./updateEvents/updateEvent.component";
import {TaggingComponent} from "./tagging/tagging.component";
import {MaintenanceComponent} from "./maintenance/maintenance.component";
import {FeedbackComponent} from "./feedback/feedback.component";
import {ShowFeedbackComponent} from "./feedback/showFeedback.component";
import {AnalyticReportEventComponent} from "./analyticReport/analyticReportEvent.component";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    EventInventoryComponent,
    AddEventComponent,
    SearchServiceComponent,
    AlertComponent,
    CalendarComponent,
    UpdateEventComponent,
    TaggingComponent,
    MaintenanceComponent,
    FeedbackComponent,
    ShowFeedbackComponent,
    AnalyticReportEventComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatTableModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    FontAwesomeModule,
    NgbModule,

  ],
  providers: [
    { provide: MatDialogRef,
    useValue: {}},
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    Location,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
