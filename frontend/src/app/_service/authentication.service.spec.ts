import { TestBed } from '@angular/core/testing';

import { AuthenticationService } from './authentication.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

describe('AuthenticationService', () => {
  let service: AuthenticationService;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        [HttpClientTestingModule],
        [RouterTestingModule],
      ]
    });
    service = TestBed.inject(AuthenticationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // Sam
  it('calling isAdmin should return correct value', () => {
    sessionStorage.setItem('role', 'USER')
    expect(service.isAdmin()).toBe(false);

    sessionStorage.setItem('role', 'ADMIN')
    expect(service.isAdmin()).toBe(true);
  });

  // Sam
  it('calling isLoggedIn should return correct value', () => {
    sessionStorage.setItem('username', 'Boris')
    expect(service.userIsloggedIn()).toBe(true);

    sessionStorage.removeItem('username')
    expect(service.userIsloggedIn()).toBe(false);
  });
});
