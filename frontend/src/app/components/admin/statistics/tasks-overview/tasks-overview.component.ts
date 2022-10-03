import { Component, OnInit } from '@angular/core';
import {TaskService} from "../../../../_service/task.service";
import {Column} from "ngx-d-table";
import {formatDate} from "@angular/common";
import {format} from "date-fns";

@Component({
  selector: 'app-tasks-overview',
  templateUrl: './tasks-overview.component.html',
  styleUrls: ['./tasks-overview.component.scss']
})
export class TasksOverviewComponent implements OnInit {
  tasks;
  columns  = [
    { name: 'name', title: 'Naam', format: '' } as Column,
    { name: 'deadline', title: 'Deadline', format: '' } as Column,
    { name: 'notSubmitted', title: 'Niet Ingeleverd', format: '' } as Column,
  ];

  constructor(private taskService:TaskService) { }

  ngOnInit(): void {
    this.fetchTasks()
  }

  async fetchTasks() {
    this.tasks = await this.taskService.getPastDeadlineTasks(new Date())
    this.tasks.forEach(val => {
      val.deadline = format(Date.parse(val.deadline), 'dd/MM/yyyy - HH:mm')
    })
  }

}
