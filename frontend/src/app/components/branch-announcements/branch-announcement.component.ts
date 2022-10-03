import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../_service/api.service';
import {Column} from 'ngx-d-table';
import {endOfTomorrow, format, startOfDay} from 'date-fns';

@Component({
  selector: 'app-announcement',
  templateUrl: './branch-announcement.component.html',
  styleUrls: ['./branch-announcement.component.scss']
})
export class BranchAnnouncementComponent implements OnInit {
  announcements: any[];
  scope: Date[];
  scopeDate: Date;
  columns = [
    {name: 'topic', title: 'Onderwerp', format: ''} as Column,
    {name: 'description', title: 'Beschrijving', format: ''} as Column,
  ];

  constructor(private apiService: ApiService) {
  }

  ngOnInit(): void {
    this.scopeDate = new Date();
    const inner = startOfDay(this.scopeDate);
    const outer = endOfTomorrow();
    this.scope = [inner, outer];


    const user = sessionStorage.getItem('branch');

    const innerDate = this.scope[0].toISOString().split('.')[0];
    const outerDate = this.scope[1].toISOString().split('.')[0];


    this.apiService.getAllAnnouncementsForBranchBetweenPeriod(user, innerDate, outerDate).then((data) => {
      this.announcements = data;
      this.announcements.forEach(val => {
        val.startDate = format(Date.parse(val.startDate), 'dd/MM/yyyy - HH:mm')
        val.endDate = format(Date.parse(val.endDate), 'dd/MM/yyyy - HH:mm')
      });
    });
  }

}
