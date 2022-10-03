import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../../../_service/api.service';
import {Column} from 'ngx-d-table';


@Component({
  selector: 'app-contact-overview',
  templateUrl: './contact-overview.component.html',
  styleUrls: ['./contact-overview.component.scss']
})
export class ContactOverviewComponent implements OnInit {
  contacts: [];
  columns  = [
    { name: 'companyName', title: 'Bedrijf', format: '' } as Column,
    { name: 'contact', title: 'Contactpersoon', format: '' } as Column,
    { name: 'phone', title: 'Telefoonnummer', format: '' } as Column,
    { name: 'email', title: 'Email', format: '' } as Column,
    { name: 'branches', title: 'Filialen', format: '' } as Column,
  ];
  selectedContact: number;

  constructor(private apiService: ApiService) {

  }

  ngOnInit(): void {
    this.apiService.getAllContacts().then((data) => {
      this.contacts = data;
    });
  }

  // public singleItem(item): void {
  //   this.selectedContact = item.id;
  //   this.showModal = true;
  // }
}
