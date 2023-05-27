import {Injectable, NgModule} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate, Router,
  RouterModule,
  RouterStateSnapshot,
  Routes
} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {EventInventoryComponent} from "./eventInventory/eventInventory.component";
import {SearchServiceComponent} from "./searchService/searchService.component";

import {Observable} from "rxjs";
import {LoginComponent} from "./account/login.component";
import {RegisterComponent} from "./account/register.component";
import {AuthGuard} from "./interceptors/auth.guard";
import {AddEventComponent} from "./addEvents/addEvent.component";
import {CalendarComponent} from "./calendar/calendar.component";
import {OrganizerGuard} from "./interceptors/organizer.guard";
import {AttendeeGuard} from "./interceptors/attendee.guard";


const accountModule = () => import('./account/account.module').then(x => x.AccountModule);

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'eventInventory', component: EventInventoryComponent, canActivate: [AuthGuard]},
  {path: 'addEvent', component: AddEventComponent, canActivate: [AuthGuard]},
  {path: 'searchService', component: SearchServiceComponent, canActivate: [AuthGuard]},
  {path: 'account', loadChildren: accountModule },
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'calendar', component: CalendarComponent, canActivate: [AuthGuard]},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

