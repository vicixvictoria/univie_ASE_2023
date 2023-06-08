import { Component } from '@angular/core';


import {AccountService} from "../../services/account.service";
import {User} from "../models/user";


@Component({ templateUrl: 'home.component.html' })
export class HomeComponent {
  user: User | null;

  constructor(private accountService: AccountService) {
    this.user = this.accountService.userValue;
  }
}
