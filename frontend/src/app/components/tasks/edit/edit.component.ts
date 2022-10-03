import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {ApiService} from "../../../_service/api.service";
import {Task} from "../../../_models/Task";
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
  isAdmin: boolean;
  taskForm: FormGroup;
  questionsArray: FormArray;
  minDeadline: Date;
  branches: string[];
  task: Task;
  taskId: string;


  constructor(private activatedRoute: ActivatedRoute, private fb: FormBuilder, private apiService: ApiService, private router: Router) {

  }

  ngOnInit(): void {
    this.isAdmin = sessionStorage.getItem('role') === 'ADMIN';
    this.minDeadline = new Date();
    this.minDeadline.setTime(this.minDeadline.getTime() + (60 * 60 * 1000));
    this.taskForm = this.fb.group({
      name: '',
      deadline: '',
      description: '',
      assignees: '',
      submittedBy: this.fb.array([]),
      questions: this.fb.array([])
    });
    this.taskId = this.activatedRoute.snapshot.params.task;
    this.apiService.getTaskById(this.taskId).then((data) => {
      this.task = Task.fromJson(data);
      this.getBranches();
      this.initForm();
    })
  }

  newQuestion(name: string, type: string, options: string): FormGroup {
    return this.fb.group({
      name: name,
      type: type,
      options: options
    });
  }

  addQuestion(name: string, type: string, options: string[]) {
    this.questionsArray = this.taskForm.get('questions') as FormArray;
    let option = '';
    if (options != null) option += options.join(';');
    this.questionsArray.push(this.newQuestion(name, type, option));
  }

  // removeQuestion(i:number) {
  //   this.questions().removeAt(i);
  // }

  onSubmit() {
    if (this.checkIfValid()) {
      let body = this.taskForm.value
      if (!Array.isArray(body.assignees)) body.assignees = [body.assignees];
      for (const element of body.questions) {
        if (element.type === 'choices') element.options = element.options.split(';');
        else element.options = null;
      }
      // console.log(body)
      this.apiService.updateTask(this.taskId, body).then((data) => {
        // @ts-ignore
        if (data.name === body.name) {
          alert('Succesvol aangepast!')
          this.router.navigate(['/taak/'+this.taskId])
        } else alert('Er is iets misgegaan!')
      });
    } else alert('Ingevoerde gegevens zijn ongeldig!')
  }

  checkIfValid(): boolean {
    let name = this.taskForm.get('name').value as string
    let deadline = this.taskForm.get('deadline').value as string
    return name != null && name.length != 0 && deadline != null && deadline.length != 0
  }

  getBranches() {
    this.apiService.getAllBranches().then((data) => {
      this.branches = [];
      for (let branch of data) {
        this.branches.push(branch.username)
      }
    });
  }

  deadlineChanged() {
    let date = Date.parse(this.taskForm.get('deadline').value)
    this.taskForm.get('deadline').setValue(date)
  }

  get name() {
    return this.taskForm.get('name')
  }

  get deadline() {
    return this.taskForm.get('deadline')
  }

  get description() {
    return this.taskForm.get('Beschrijving')
  }

  get assignees() {
    return this.taskForm.get('deadline')
  }

  resetForm() {
    this.ngOnInit();
  }

  test(item: any) {
    console.log(item)
  }

  private initForm() {
    this.taskForm.get('name').setValue(this.task.name)
    this.taskForm.get('deadline').setValue(this.task.deadline)
    this.taskForm.get('description').setValue(this.task.description)
    this.taskForm.get('assignees').setValue(this.task.assignees as unknown as string[])
    for (const question of this.task.questions) {
      this.addQuestion(question.name,question.type,question.options)
    }
  }
}
