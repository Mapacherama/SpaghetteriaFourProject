import {Injectable} from '@angular/core';
import {ApiService} from "./api.service";
import {HttpClient} from "@angular/common/http";
import {TokenInterceptor} from "./token-interceptor.service";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class TaskService extends ApiService {

  constructor(httpClient: HttpClient, httpIntercepter: TokenInterceptor) {
    super(httpClient, httpIntercepter);
  }

  getPastDeadlineTasks(date: Date) {
    const path = `admin/task/getOutstandingPastDeadline?date=`;
    return this.httpClient.get(ApiService.baseURL + path + date.toISOString().split('T')[0]).toPromise();
  }

  getDailyReports(date: Date): Promise<any> {
    const path = `admin/reports/getStatsForDay?date=`;
    return this.httpClient.get(ApiService.baseURL + path + date.toISOString().split('T')[0]).toPromise();
  }

  getRecurringTaskById(id: String): Promise<any> {
    return this.httpClient.get(ApiService.baseURL + `admin/task/recurring/get?task=${id}`).toPromise();
  }

  updateRecurringTask(task) {
    return this.httpClient.post(ApiService.baseURL + `admin/task/recurring/update`, task).toPromise();
  }
}
