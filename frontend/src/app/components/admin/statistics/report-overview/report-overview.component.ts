import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../../../_service/api.service";
import {Router} from "@angular/router";
import {TaskService} from "../../../../_service/task.service";
import {Column} from "ngx-d-table";

@Component({
  selector: 'app-report-overview',
  templateUrl: './report-overview.component.html',
  styleUrls: ['./report-overview.component.scss']
})
export class ReportOverviewComponent implements OnInit {
  data: any;
  branches: string[];
  columns  = [
    { name: 'branch', title: 'Filiaal', format: '' } as Column,
    { name: 'submitted', title: 'Ingeleverd', format: 'Boolean' } as Column,
  ];

  constructor(private taskService:TaskService, private router:Router) { }

  ngOnInit(): void {
    this.fetchReports()
  }

  fetchReports() {
    this.branches = [];

    let date = new Date();
    date = new Date(date.setDate(date.getDate()-1));

    this.taskService.getDailyReports(date).then(data => {
      this.data = data;
      this.branches = data.branches;
    })
  }

  viewReport(branch: string): string {
    sessionStorage.setItem('branch', branch)
    return `/taak/${this.data.id}`;
  }



}
