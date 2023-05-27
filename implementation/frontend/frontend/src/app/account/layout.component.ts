import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AccountService} from "../../services/account.service";

//Registration and Login based on https://jasonwatmore.com/post/2022/11/29/angular-14-user-registration-and-login-example-tutorial#app-module-ts
@Component({ templateUrl: 'layout.component.html' })
export class LayoutComponent {
  constructor(
    private router: Router,
    private accountService: AccountService
  ) {
    // redirect to home if already logged in
    if (this.accountService.userValue) {
      this.router.navigate(['/']);
    }
  }
}
