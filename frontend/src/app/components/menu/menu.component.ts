import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../_service/authentication.service";
import {Observable} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  isLoggedIn: Observable<boolean>;
  branch: string;
  route: String;

  constructor(private authenticationService: AuthenticationService, router: Router) {
    router.events.subscribe(val => {
      this.route = this.capitalize(router.url.split('/')[1]);
      this.branch = sessionStorage.getItem('branch');
    });
  }

  ngOnInit(): void {
    this.isLoggedIn = this.authenticationService.isLoggedIn;
    this.branch = sessionStorage.getItem('branch');
  }

  onLogout(){
    this.authenticationService.logOut();
  }

  capitalize(str): String {
    return str.charAt(0).toUpperCase() + str.slice(1);
  }

}
