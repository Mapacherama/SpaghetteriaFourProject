import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ApiService} from "../../../_service/api.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
  addUserForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private apiService: ApiService, private router: Router) {
    this.addUserForm = formBuilder.group({
      username: '',
      password: '',
      password2: '',
      role: 'USER',
      enabled: true
    });
  }

  ngOnInit(): void {

  }

  addUser(): void {
    const username = this.addUserForm.get('username').value;
    const password = this.addUserForm.get('password').value;
    const password2 = this.addUserForm.get('password2').value;
    const role = this.addUserForm.get('role').value;
    const enabled = this.addUserForm.get('enabled').value;


    if (username === "" || password === "" || role === "") {
      alert('Naam of wachtwoord kan niet leeg zijn!');
      return;
    } else if (password !== password2) {
      alert('Wachtwoorden komen niet overeen!');
      return;
    }

    const userDto = {
      username,
      password,
      role,
      enabled
    }
    // console.log(userDto)

    this.apiService.createUser(userDto).then((data) => {
      if (data.username === userDto.username) {
        alert('Succesvol toegevoegd!');
        this.router.navigate(['/admin/panel'])
      } else alert('Gegevens niet geldig of al in gebruik!');
    });
  }
}
