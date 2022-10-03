import {Component, OnInit} from '@angular/core';
import {Task} from 'src/app/_models/Task';
import {ApiService} from '../../../_service/api.service';
import {endOfISOWeek, endOfWeek, getISOWeek, getWeek, startOfISOWeek} from 'date-fns'
import {isElementScrolledOutsideView} from '@angular/cdk/overlay/position/scroll-clip';

@Component({
  selector: 'app-calender',
  templateUrl: './calender.component.html',
  styleUrls: ['./calender.component.scss']
})
export class CalenderComponent implements OnInit {
  tasks: Task[];
  scope: Date[];
  scopeDate: Date;
  week: number;

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.scopeDate = new Date();
    this.updateWeek(0)
  }

  getRangeForDate(): void {
    const inner = startOfISOWeek(this.scopeDate);
    const outer = endOfISOWeek(this.scopeDate);
    this.scope = [inner, outer];
  }

  updateWeek(upOrDown: number): void {
    this.tasks = [];

    if (upOrDown < 0) {
      this.scopeDate.setDate(this.scopeDate.getDate() - 7);
    } else if (upOrDown > 0) {
      this.scopeDate.setDate(this.scopeDate.getDate() + 7);
    }

    this.scope = [startOfISOWeek(this.scopeDate), endOfISOWeek(this.scopeDate)];
    this.week = getISOWeek(this.scopeDate);

    this.scope[0].setHours(12);
    this.scope[1].setHours(12);

    this.getTasks();
  }

  getTasks(): void {
    const inner = this.scope[0].toISOString().split('T')[0];
    const outer = this.scope[1].toISOString().split('T')[0];

    this.apiService.getAllTasksInPeriod(inner, outer).then((data) => {
      this.tasks = [];
      for (const t of data) {
        this.tasks.push(Task.fromJson(t));
      }
    });
  }

  getStatus(n: Task): string {
    let finished;
    for (const assignee of n.submittedBy) {
      if (assignee === sessionStorage.getItem('branch')) { finished = true; }
    }
    if (finished) { return `check_circle_outline`; }
    else { return `schedule`; }
  }

  getTaskColor(n: Task): string {
    let finished;
    for (const assignee of n.submittedBy) {
      if (assignee === sessionStorage.getItem('branch')) { finished = true; }
    }
    if (finished) { return `gray`; }
    else { return `green`; }
  }

  getTasksOnDay(day: number): Task[] {
    if (this.tasks === undefined) return [];
    const taskArray: Task[] = [];
    for (const task of this.tasks) {
      if (task.deadline.getDay() === day) { taskArray.push(task) }
    }
    return taskArray;
  }

  getDate(dayOfWeek: number): string {
    const months = ['Januari','Februari','Maart','April','Mei','Juni','Juli','Augustus','September','Oktober','November','December']
    const days = ['Zondag','Maandag','Dinsdag','Woensdag','Donderdag','Vrijdag','Zaterdag']
    const date = new Date(this.scope[0])
    date.setDate(date.getDate() + dayOfWeek);

    return `${days[date.getDay()]} ${date.getDate()} ${months[date.getMonth()]}`
  }
}
