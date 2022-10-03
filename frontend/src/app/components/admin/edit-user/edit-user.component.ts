import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ApiService} from "../../../_service/api.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  @Input() user: string;
  infoForm: FormGroup;
  passwordForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private apiService: ApiService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.infoForm = formBuilder.group({
      username: '',
      role: 'USER',
      enabled: false
    });

    this.passwordForm = formBuilder.group({
      password: '',
      password2: ''
    });
  }

  ngOnInit(): void {
    if (this.user === undefined && this.activatedRoute.snapshot.params.id !== undefined) {
      this.user = this.activatedRoute.snapshot.params.id;
    }

    this.apiService.getUser(this.user).then((data) => {
      this.infoForm = this.formBuilder.group({
        username: data.username,
        role: data.role,
        enabled: data.enabled
      });
    });
  }

  updateInfo(): void {
    const username = this.infoForm.get('username').value;
    const role = this.infoForm.get('role').value;
    const enabled = this.infoForm.get('enabled').value;


    if (username === "" ) {
      alert('Naam kan niet leeg zijn!');
      return;
    }

    const userDto = {
      username,
      role,
      enabled
    }

    this.apiService.updateUser(this.user, userDto).then((data) => {
      if (data.username === userDto.username) {
        alert('Succesvol aangepast!');
        this.router.navigate(['/admin/panel'])
      } else alert('Gegevens niet geldig of al in gebruik!');
    });
  }

  updatePassword(): void {
    let username = this.user;
    const password = this.passwordForm.get('password').value;
    const password2 = this.passwordForm.get('password2').value;

    if (password !== password2) {
      alert('Wachtwoorden komen niet overeen!');
      return;
    }

    const userDto = {
      username,
      password
    }

    this.apiService.updatePassword(this.user, userDto).then((data) => {
      if (data.username === userDto.username) {
        alert('Succesvol aangepast!');
        this.router.navigate(['/admin/panel'])
      } else alert('Gegevens niet geldig!');
    });
  }
}
