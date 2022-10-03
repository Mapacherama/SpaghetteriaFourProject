import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, snap: RouterStateSnapshot) {
    if (sessionStorage.getItem("username") == null) {
      this.router.navigate(["/login"]);
      return false;
    }
    return true;
  }
}
