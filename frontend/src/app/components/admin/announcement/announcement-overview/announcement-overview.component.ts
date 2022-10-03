import { Component, OnInit } from '@angular/core';
import {Column} from 'ngx-d-table';
import {ApiService} from '../../../../_service/api.service';
import {format} from 'date-fns';

@Component({
  selector: 'app-announcement-overview',
  templateUrl: './announcement-overview.component.html',
  styleUrls: ['./announcement-overview.component.scss']
})
export class AnnouncementOverviewComponent implements OnInit {
  announcements: any[];
  columns  = [
    { name: 'topic', title: 'Onderwerp', format: '' } as Column,
    { name: 'description', title: 'Beschrijving', format: '' } as Column,
    { name: 'startDate', title: 'Van', format: '' } as Column,
    { name: 'endDate', title: 'Tot', format: '' } as Column,
  ];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.api.getAllAnnouncements().then((data) => {
      this.announcements = data;
      this.announcements.forEach(val => {
        val.startDate = format(Date.parse(val.startDate), 'dd/MM/yyyy - HH:mm')
        val.endDate = format(Date.parse(val.endDate), 'dd/MM/yyyy - HH:mm')
      });
    });
  }

}
