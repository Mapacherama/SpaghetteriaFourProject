import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AdminGuardService implements CanActivate {

  constructor(private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, snap: RouterStateSnapshot) {
    if (sessionStorage.getItem("username") == null) {
      this.router.navigate(["/login"]);
      return false;
    } else if (sessionStorage.getItem("role") != "ADMIN") {
      this.router.navigate(["/home"]);
      return false;
    }
    return true;
  }
}
