import {ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import { AddUserComponent } from './add-user.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {validAdmin} from "../../../_mocks";

const apiServiceSpy = jasmine.createSpyObj('ApiService', ['createUser']);
const routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);

describe('UserManagmentComponent', () => {
  let component: AddUserComponent;
  let fixture: ComponentFixture<AddUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        [HttpClientTestingModule],
        [RouterTestingModule],
        [FormsModule],
        [ReactiveFormsModule]
      ],
      declarations: [ AddUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Sam
  it('Submit should not call apiService.CreateUser if passwords dont match', fakeAsync(() => {
    let dto = { username:'boris', password:'boris123', password2:'boris321', role:'ADMIN', enabled:true }

    updateForm(dto.username, dto.password, dto.password2, dto.role, dto.enabled);

    fixture.detectChanges();

    const button = fixture.debugElement.nativeElement.querySelector('#submit');
    button.click();

    fixture.detectChanges();

    expect(apiServiceSpy.createUser).not.toHaveBeenCalled();
  }));

  // Sam
  it('Submit should call AddUser if valid', fakeAsync(() => {
    spyOn(fixture.componentInstance, 'addUser');
    let dto = { username:'boris', password:'boris123', password2:'boris123', role:'ADMIN', enabled:true }

    updateForm(dto.username, dto.password, dto.password2, dto.role, dto.enabled);

    fixture.detectChanges();

    const button = fixture.debugElement.nativeElement.querySelector('#submit');
    button.click();

    fixture.detectChanges();

    expect(fixture.componentInstance.addUser).toHaveBeenCalled();
  }));

  function updateForm(username, password, password2, role, enabled) {
    fixture.componentInstance.addUserForm.controls['username'].setValue(username);
    fixture.componentInstance.addUserForm.controls['password'].setValue(password);
    fixture.componentInstance.addUserForm.controls['password2'].setValue(password2);
    fixture.componentInstance.addUserForm.controls['role'].setValue(role);
    fixture.componentInstance.addUserForm.controls['enabled'].setValue(enabled);
  }
});
