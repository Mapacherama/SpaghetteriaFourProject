import { Component, OnInit } from '@angular/core';
import {ImportantContacts} from '../../_models/ImportantContacts';
import {ApiService} from '../../_service/api.service';
import {ActivatedRoute} from '@angular/router';
import {Column} from "ngx-d-table";

@Component({
  selector: 'app-view-contact',
  templateUrl: './view-contact.component.html',
  styleUrls: ['./view-contact.component.scss']
})
export class ViewContactComponent implements OnInit {
  contacts: [];
  columns  = [
    { name: 'companyName', title: 'Bedrijf', format: '' } as Column,
    { name: 'contact', title: 'Contactpersoon', format: '' } as Column,
    { name: 'phone', title: 'Telefoonnummer', format: '' } as Column,
    { name: 'email', title: 'Email', format: '' } as Column,
  ];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    // this.contacts = [];
    const user = sessionStorage.getItem('branch');

    this.apiService.getAllContactsForBranch(user).then((data) => {
      this.contacts = data;
    });
  }



}
