import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../_service/api.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  tasks: [];
  isAdmin: boolean;
  branch: string;

  constructor(private apiService:ApiService) { }

  ngOnInit(): void {
    this.isAdmin = sessionStorage.getItem('role') === 'ADMIN';
    this.branch = sessionStorage.getItem('branch');
    this.apiService.getAllUnfinishedTasks().then((data) => {
      data.sort(function(a,b){
        // @ts-ignore
        return a.deadline - b.deadline;
      })
      this.tasks = data;
    })
  }

  getStatus(n: string): String {
    let deadline = new Date(Date.parse(n));
    let currentTime = new Date();
    let currentTimePlus1H = new Date(currentTime);
    currentTimePlus1H.setHours(currentTimePlus1H.getHours()+1)
    if (currentTime > deadline) return `error`
    else if (currentTimePlus1H > deadline) return `error_outline`
    else return `schedule`
  }

  getTaskColor(n: string): String {
    let deadline = new Date(Date.parse(n));
    let currentTime = new Date();
    let currentTimePlus1H = new Date(currentTime);
    currentTimePlus1H.setHours(currentTimePlus1H.getHours()+1)
    if (currentTime > deadline) return `red`
    else if (currentTimePlus1H > deadline) return `yellow`
    else return `green`
  }

}
