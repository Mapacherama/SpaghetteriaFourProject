import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ApiService} from "../../../_service/api.service";
import {Task} from "../../../_models/Task";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.scss']
})
export class ViewTaskComponent implements OnInit {
  task: Task;
  taskId: string;
  taskForm: FormGroup;
  submission: any;
  isFinished: boolean;
  isAdmin: boolean;
  submitted: boolean;


  constructor(private activatedRoute: ActivatedRoute, private apiService: ApiService, private formBuilder: FormBuilder, private sanitizer: DomSanitizer, private router: Router) {

  }

  ngOnInit(): void {
    this.isAdmin = sessionStorage.getItem('role') === 'ADMIN'
    this.taskId = this.activatedRoute.snapshot.params.task;
    this.apiService.getTaskById(this.taskId).then((data) => {
      this.apiService.getSubmissionById(this.taskId).then(async (data2) => {
        this.submission = data2;
        let formgroup = await this.getFormGroup(data2.answers)
        this.taskForm = this.formBuilder.group(formgroup);
        this.checkIfFinished();

        for (const assignee of this.task.submittedBy) {
          if (assignee === sessionStorage.getItem('branch')) {
            this.submitted = true;
            this.taskForm.disable();
          }
        }
      })
      this.task = Task.fromJson(data);
    })

  }

  onSubmit(): void {
    if (this.isFinished) {
      this.apiService.setTaskAsSubmitted(this.taskId).then((data) => {
        if (data._id === this.taskId) {
          alert('Succesvol ingeleverd!');
          this.router.navigate(['/home']);
        } else {
          alert('Er is iets misgegaan!');
        }
      });
    }
  }

  getFormGroup(arr: string[]): any {
    let object = "{"
    for (let i = 0; i < this.task.questions.length; i++) {
      let answer = arr[i];
      if (answer === null) answer = ''
      if (i + 1 === this.task.questions.length) object += `"${i}":["${answer}"]`
      else object += `"${i}":["${answer}"],`
    }
    object += "}"
    return JSON.parse(object);
  }

  onChange(e: any, i: number) {
    this.submission.answers[i] = e.target.value;
    this.taskForm.get(i.toString()).setValue(e.target.value)
    this.apiService.setAnswer(this.task._id.toString(), e.target.value, i);
    this.checkIfFinished();
  }

  private checkIfFinished(): void {
    for (const field in this.taskForm.value) {
      const element = this.taskForm.get(field);
      if (element.value === null || element.value === '') {
        this.isFinished = false;
        return;
      }
    }
    this.isFinished = true;
  }

  getUrl(i: number) {
    return this.sanitizer.bypassSecurityTrustUrl(ApiService.baseURL + 'api/getImage?id=' + this.submission.answers[i]);
  }

  onImageChange(e: any, i: number) {
    // @ts-ignore
    let data = document.getElementById(i.toString()).files[0];
    this.apiService.uploadImage(data).then((data2) => {
      const url = data2.body + '';
      this.apiService.setAnswer(this.task._id.toString(), url, i)
      this.taskForm.get(i.toString()).setValue(url);
      this.submission.answers[i] = url;
      document.getElementById('photo' + i).setAttribute('src', ApiService.baseURL + 'api/getImage?id=' + url)
    })
  }

  onFileChange(e: any, i: number) {
    // @ts-ignore
    let data = document.getElementById(i.toString()).files[0];
    this.apiService.uploadFile(data).then((data2) => {
      const url = data2.body + '';
      this.apiService.setAnswer(this.task._id.toString(), url, i)
      this.taskForm.get(i.toString()).setValue(url);
      this.submission.answers[i] = url;
      document.getElementById('file' + i).setAttribute('href', ApiService.baseURL + 'api/getFile?id=' + url)
    })
  }

  /*onUpload(e:any, i:number){
    var reader = new FileReader();
    // @ts-ignore
    this.imgService.resizeImage(document.getElementById(i.toString()).files[0],800,800,{exifOptions:{forceExifOrientation:true}, aspectRatio:{keepAspectRatio:true}}).subscribe((data) => {
      reader.readAsDataURL(data);
      reader.onload = () => {
        this.submission.answers[i] = reader.result;
        this.taskForm.get(i.toString()).setValue(reader.result);
        document.getElementById('photo'+i).setAttribute('src',reader.result.toString())
        this.apiService.setAnswer(this.task._id.toString(),reader.result.toString(),i)
      }
      reader.onerror = function (error) {
        console.log('Error: ', error);
      };
    })
  }*/

  pressclick(i: number) {
    document.getElementById(i.toString()).click()
  }

  getFileUrl(i: number) {
    return this.sanitizer.bypassSecurityTrustUrl(ApiService.baseURL + 'api/getFile?id=' + this.submission.answers[i]);
  }
}
