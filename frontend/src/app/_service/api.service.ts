import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TokenInterceptor} from './token-interceptor.service';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  static baseURL: String = `${environment.apiUrl}`;

  constructor(protected httpClient: HttpClient, protected httpIntercepter: TokenInterceptor) {

  }

  async getAllBranches(): Promise<any> {
    return this.httpClient.get(ApiService.baseURL + 'admin/getAllBranches').pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getUser(user: string): Promise<any> {
    return this.httpClient.get(ApiService.baseURL + 'admin/user/get?user=' + user).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async createUser(userDto): Promise<any> {
    return this.httpClient.post(ApiService.baseURL + 'admin/user/add', userDto).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async updateUser(user: string, userDto): Promise<any> {
    return this.httpClient.post(ApiService.baseURL + 'admin/user/update?user=' + user, userDto).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async updatePassword(user: string, userDto): Promise<any> {
    return this.httpClient.post(ApiService.baseURL + 'admin/user/updatePassword?user=' + user, userDto).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getTasks(): Promise<any> {
    if (sessionStorage.getItem('role') !== 'ADMIN') {
      return;
    }
    return this.httpClient.get(ApiService.baseURL + 'admin/task/getAll').pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getRecurringTasks(): Promise<any> {
    if (sessionStorage.getItem('role') !== 'ADMIN') {
      return;
    }
    return this.httpClient.get(ApiService.baseURL + 'admin/task/getAllRecurring').pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllTasks(): Promise<any> {
    let path = 'branch/task/getAll';
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/task/getAllFor?branch=${sessionStorage.getItem('branch')}`;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllUnfinishedTasks(): Promise<any> {
    let path = 'branch/task/getUnfinished';
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/task/getUnfinishedFor?branch=${sessionStorage.getItem('branch')}`;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();

  }

  async getTaskById(id: String): Promise<any> {
    return this.httpClient.get(ApiService.baseURL + `branch/task/getById?task=${id}`).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getSubmissionById(id: String): Promise<any> {
    let path = `branch/task/submission/getById?task=${id}`;
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/task/submission/getById?task=${id}&branch=${sessionStorage.getItem('branch')}`;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async setAnswer(task: string, answer: any, index: number): Promise<any> {
    const branch = sessionStorage.getItem('branch');
    return this.httpClient.post(ApiService.baseURL + 'branch/task/submission/setanswer', {task, branch, answer, index}).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async uploadImage(selectedFile) {
    // FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, selectedFile.name);

    // Make a call to the Spring Boot Application to save the image
    return this.httpClient.post(ApiService.baseURL + 'branch/uploadImage', uploadImageData, {observe: 'response'}).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async uploadFile(selectedFile) {
    // FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('file', selectedFile, selectedFile.name);

    // Make a call to the Spring Boot Application to save the image
    return this.httpClient.post(ApiService.baseURL + 'branch/uploadFile', uploadImageData, {observe: 'response'}).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async createTask(task) {
    return this.httpClient.post(ApiService.baseURL + 'branch/task/create', task).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async createRecurringTask(task) {
    return this.httpClient.post(ApiService.baseURL + 'admin/task/createRecurring', task).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async updateTask(taskid, task) {
    return this.httpClient.post(ApiService.baseURL + 'admin/task/update?task=' + taskid, task).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async setTaskAsSubmitted(task: string): Promise<any> {
    let path = `branch/task/submit?task=${task}`;
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/task/submit?task=${task}&branch=${sessionStorage.getItem('branch')}`;
    }
    return this.httpClient.post(ApiService.baseURL + path, {}).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllContactsForBranch(user: String): Promise<any> {
    let path = 'branch/getContacts';
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/getAllContactsForBranch?branch=${user}`;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllProducts(): Promise<any> {
    const path = `branch/getAllProducts`;
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllAnnouncements(): Promise<any> {
    const path = `admin/getAllAnnouncements`;
    if (sessionStorage.getItem('role') !== 'ADMIN') {
      return;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllAnnouncementsForBranch(user: String): Promise<any> {
    let path = 'branch/getAnnouncements';
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/getAllAnnouncementsForBranch?branch=${user}`;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllAnnouncementsForBranchBetweenPeriod(user: String, inner: String, outer: String): Promise<any> {
    let path = `branch/getAnnouncementsBetweenPeriod?date1=${inner}&date2=${outer}`;
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/getAnnouncementBetweenPeriodFor?branch=${user}&date1=${inner}&date2=${outer}`;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }



  async addAnnouncement(announcement): Promise<any> {
    if (sessionStorage.getItem('role') === 'ADMIN') {
      return this.httpClient.post(ApiService.baseURL + 'admin/addAnnouncement', announcement).pipe(
        map(
          data => {
            return data;
          }
        )
      ).toPromise();
    }
  }

  async updateAnnouncement(announcement): Promise<any> {
    if (sessionStorage.getItem('role') === 'ADMIN') {
      return this.httpClient.post(ApiService.baseURL + 'admin/updateAnnouncement', announcement).pipe(
        map(
          data => {
            return data;
          }
        )
      ).toPromise();
    }
  }

  async deleteAnnouncement(id): Promise<any> {
    if (sessionStorage.getItem('role') === 'ADMIN') {
      return this.httpClient.get(ApiService.baseURL + 'admin/deleteAnnouncement?id=' + id).pipe(
        map(
          data => {
            return data;
          }
        )
      ).toPromise();
    }
  }

  async getDetailedAnnouncement(id: number): Promise<any> {
    const path = 'admin/getAnnouncementById?';
    if (sessionStorage.getItem('role') !== 'ADMIN') {
      return;
    }
    return this.httpClient.get(ApiService.baseURL + path + `id=${id}`).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  updateProduct(product): Promise<any> {
    const path = `admin/dish/update`;
    return this.httpClient.post(ApiService.baseURL + path, product).toPromise();
  }

  deleteProduct(id): Promise<any> {
    const path = `admin/deleteProductById`;
    return this.httpClient.delete(ApiService.baseURL + path + `?id=${id}`).toPromise();
  }

  createProduct(product): Promise<any> {
    const path = `admin/createProduct`;
    return this.httpClient.post(ApiService.baseURL + path, product).toPromise();
  }

  getProductById(id: number): Promise<any> {
    const path = `branch/product/getById`;
    return this.httpClient.get(ApiService.baseURL + path + `?id=${id}`).toPromise();
  }

  async getAllContacts(): Promise<any> {
    const path = 'admin/getAllContacts';
    if (sessionStorage.getItem('role') !== 'ADMIN') {
      return;
    }
    return this.httpClient.get(ApiService.baseURL + path).toPromise();
  }

  async getDetailedContact(id: number): Promise<any> {
    const path = 'admin/getContactById?';
    if (sessionStorage.getItem('role') !== 'ADMIN') {
      return;
    }
    return this.httpClient.get(ApiService.baseURL + path + `id=${id}`).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async getAllTasksInPeriod(inner: String, outer: String): Promise<any> {
    let path = `branch/task/submission/getBetweenPeriod?date1=${inner}&date2=${outer}`;
    if (sessionStorage.getItem('role') === 'ADMIN') {
      path = `admin/task/submission/getBetweenPeriodFor?branch=${sessionStorage.getItem('branch')}&date1=${inner}&date2=${outer}`;
    }
    return this.httpClient.get(ApiService.baseURL + path).pipe(
      map(
        data => {
          return data;
        }
      )
    ).toPromise();
  }

  async updateContact(contact): Promise<any> {
    if (sessionStorage.getItem('role') === 'ADMIN') {
      return this.httpClient.post(ApiService.baseURL + 'admin/updateContact', contact).pipe(
        map(
          data => {
            return data;
          }
        )
      ).toPromise();
    }
  }

  async addContact(contact): Promise<any> {
    if (sessionStorage.getItem('role') === 'ADMIN') {
      return this.httpClient.post(ApiService.baseURL + 'admin/addContact', contact).pipe(
        map(
          data => {
            return data;
          }
        )
      ).toPromise();
    }
  }

  async deleteContact(id): Promise<any> {
    if (sessionStorage.getItem('role') === 'ADMIN') {
      return this.httpClient.get(ApiService.baseURL + 'admin/deleteContact?id=' + id).pipe(
        map(
          data => {
            return data;
          }
        )
      ).toPromise();
    }
  }

  async getAllUsers(activeOnly: boolean, adminOnly: boolean): Promise<any> {
    if (sessionStorage.getItem('role') === 'ADMIN') {
      return this.httpClient.get(ApiService.baseURL + 'admin/getAllUsers?activeOnly=' + activeOnly + '&adminOnly=' + adminOnly).pipe(
        map(
          data => {
            return data;
          }
        )
      ).toPromise();
    }
  }

  generateMenu(dishes: any[]) {
    return this.httpClient.post(ApiService.baseURL + 'branch/generateMenu', dishes, {responseType: 'blob'}).toPromise();
  }

  getDailyReports(date: Date): Promise<any> {
    const path = `admin/reports/getForDay?date=`;
    return this.httpClient.get(ApiService.baseURL + path + date.toISOString().split('T')[0]).toPromise();
  }

  getPastDueTasks(branch: string): Promise<any> {
    const path = `admin/task/getUnfinishedPastDeadlineFor?branch=`;
    return this.httpClient.get(ApiService.baseURL + path + branch).toPromise();
  }
}
