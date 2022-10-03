import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject} from "rxjs";
import {ApiService} from "./api.service";

export class User{
  constructor(
    public status:string,
  ) {}

}

export class JwtResponse{
  constructor(
    public jwttoken:string,
  ) {}

}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private loggedIn = new BehaviorSubject<boolean>(false);

  get isLoggedIn() {
    return this.loggedIn.asObservable(); // {2}
  }

  constructor(
    private httpClient:HttpClient,
    private router: Router
  ) {
    this.loggedIn.next(sessionStorage.getItem('username') !== null);
  }

  async authenticate(username, password) {
    let call = this.httpClient.post<any>(ApiService.baseURL + 'api/login',{username:username,password:password}).pipe(
      map(
        userData => {
          sessionStorage.setItem('username',userData.username);
          sessionStorage.setItem('role',userData.role);
          let tokenStr= 'Bearer '+userData.token;
          sessionStorage.setItem('token', tokenStr);
          return userData;
        }
      )
    ).toPromise().finally(() => {
      if (sessionStorage.getItem('username') !== null) {
        this.loggedIn.next(true);
        if (!this.isAdmin()) {
          sessionStorage.setItem('branch',sessionStorage.getItem('username'));
          this.router.navigate(['/home']); return true;
        } else {
          this.router.navigate(['/selectbranch']); return true;
        }
      }
      alert('Ongeldige gegevens.')
      return false;
    })
  }

  isAdmin() {
    let user = sessionStorage.getItem('role')
    return user === "ADMIN"
  }

  userIsloggedIn() {
    let user = sessionStorage.getItem('username')
    return !!user
  }

  logOut() {
    sessionStorage.clear()
    this.loggedIn.next(false);
    this.router.navigate(['/login'])
  }
}
