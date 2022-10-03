import {ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import { validAdmin, blankUser } from 'src/app/_mocks';
import {FormBuilder, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthenticationService} from '../../_service/authentication.service';


const loginServiceSpy = jasmine.createSpyObj('AuthenticationService', ['authenticate']);
const routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        [HttpClientTestingModule],
        [RouterTestingModule],
        [FormsModule],
        [ReactiveFormsModule]
      ],
      declarations: [LoginComponent]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
  });

  beforeEach(() => {
    component = new LoginComponent(loginServiceSpy, new FormBuilder(), routerSpy);
  });

  function updateForm(userEmail, userPassword) {
    fixture.componentInstance.loginForm.controls['username'].setValue(userEmail);
    fixture.componentInstance.loginForm.controls['password'].setValue(userPassword);
  }

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('component initial state', () => {
    expect(component.loginForm).toBeDefined();
    expect(loginServiceSpy.authenticate(component.loginForm.get('username').value, component.loginForm.get('password').value)).toBeFalsy();
  });

  it('form value should update from when u change the input', (() => {
    updateForm(validAdmin.username, validAdmin.password);
    expect(fixture.componentInstance.loginForm.value).toEqual(validAdmin);
  }));

  it('created a form with username and password input and login button', () => {
    // const fixture = TestBed.createComponent(LoginComponent);
    const usernameContainer = fixture.debugElement.nativeElement.querySelector('#username-container');
    const passwordContainer = fixture.debugElement.nativeElement.querySelector('#password-container');
    const loginBtnContainer = fixture.debugElement.nativeElement.querySelector('#login-btn-container');
    expect(usernameContainer).toBeDefined();
    expect(passwordContainer).toBeDefined();
    expect(loginBtnContainer).toBeDefined();
  });

  it('authenticationService authenticate() should be called ', fakeAsync(() => {
    spyOn(fixture.componentInstance, 'onSubmit');
    // tslint:disable-next-line:prefer-const
    updateForm(validAdmin.username, validAdmin.password);
    fixture.detectChanges();
    const button = fixture.debugElement.nativeElement.querySelector('#button');
    button.click();
    fixture.detectChanges();

    expect(fixture.componentInstance.onSubmit).toHaveBeenCalled();
  }));
});
