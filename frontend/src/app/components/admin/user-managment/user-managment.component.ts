import { Component, OnInit } from '@angular/core';
import {Column} from "ngx-d-table";
import {ApiService} from "../../../_service/api.service";

@Component({
  selector: 'app-user-managment',
  templateUrl: './user-managment.component.html',
  styleUrls: ['./user-managment.component.scss']
})
export class UserManagmentComponent implements OnInit {
  users: [];
  columns  = [
    { name: 'username', title: 'Gebruikersnaam', format: '' } as Column,
    { name: 'role', title: 'Functie', format: '' } as Column,
    { name: 'enabled', title: 'Actief', format: 'Boolean' } as Column,
  ];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getAllUsers(false, false).then((data) => {
      this.users = data;
    });
  }
}
