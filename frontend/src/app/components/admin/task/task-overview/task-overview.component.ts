import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../../../_service/api.service';
import {Column} from 'ngx-d-table';
import {format} from 'date-fns';

@Component({
  selector: 'app-task-overview',
  templateUrl: './task-overview.component.html',
  styleUrls: ['./task-overview.component.scss']
})
export class TaskOverviewComponent implements OnInit {
  tasks: any[];
  recurringTasks: any[];

  columnsTasks = [
    {name: 'name', title: 'Naam', format: ''} as Column,
    {name: 'description', title: 'Beschrijving', format: ''} as Column,
    {name: 'deadline', title: 'deadline', format: ''} as Column,
  ];

  columnsRecurringTasks = [
    {name: 'name', title: 'Naam', format: ''} as Column,
    {name: 'description', title: 'Beschrijving', format: ''} as Column,
    {name: 'startDate', title: 'Begin datum', format: ''} as Column,
    {name: 'endDate', title: 'deadline', format: ''} as Column,
    {name: 'intervalAmount', title: 'herhalings aantal', format: ''} as Column,
    {name: 'intervalUnit', title: 'herhaalt zich per', format: ''} as Column,
  ];

  constructor(private api: ApiService) {
  }

  ngOnInit(): void {
    this.api.getTasks().then((data) => {
      this.tasks = data;
      this.tasks.forEach(val => {
        val.deadline = format(Date.parse(val.deadline), 'dd/MM/yyyy - HH:mm');
      });
    });

    this.api.getRecurringTasks().then((data) => {
      this.recurringTasks = data;
      this.recurringTasks.forEach(val => {
        val.startDate = format(Date.parse(val.startDate), 'dd/MM/yyyy - HH:mm');
      });
    });
  }

}
