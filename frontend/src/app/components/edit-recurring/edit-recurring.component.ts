import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {Task} from "../../_models/Task";
import {ActivatedRoute, Router} from "@angular/router";
import {ApiService} from "../../_service/api.service";
import {TaskService} from "../../_service/task.service";

@Component({
  selector: 'app-edit-recurring',
  templateUrl: './edit-recurring.component.html',
  styleUrls: ['./edit-recurring.component.scss']
})
export class EditRecurringComponent implements OnInit {
  isAdmin: boolean;
  taskForm: FormGroup;
  questionsArray: FormArray;
  minDeadline: Date;
  branches: string[];
  task: any;
  taskId: string;


  constructor(private activatedRoute: ActivatedRoute, private fb: FormBuilder, private apiService: ApiService, private router: Router, private taskService: TaskService) {

  }

  ngOnInit(): void {
    this.isAdmin = sessionStorage.getItem('role') === 'ADMIN';
    this.taskForm = this.fb.group({
      _id: '',
      name: '',
      startDate: '',
      endDate: '',
      intervalUnit: '',
      intervalAmount: '',
      description: '',
      assignees: '',
      questions: this.fb.array([])
    });
    this.taskId = this.activatedRoute.snapshot.params.task;
    this.taskService.getRecurringTaskById(this.taskId).then((data) => {
      this.task = data;
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
      this.taskService.updateRecurringTask(body).then((data) => {
        // @ts-ignore
        if (data.name === body.name) {
          alert('Succesvol aangepast!')
          this.router.navigate(['/admin/panel'])
        } else alert('Er is iets misgegaan!')
      });
    } else alert('Ingevoerde gegevens zijn ongeldig!')
  }

  checkIfValid(): boolean {
    let name = this.taskForm.get('name').value as string
    let startDate = this.taskForm.get('startDate').value as string
    let endDate = this.taskForm.get('endDate').value as string
    return name != null && name.length != 0 && startDate != null && startDate.length != 0 && endDate != null && endDate.length != 0
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
    this.taskForm.get('_id').setValue(this.task._id)
    this.taskForm.get('name').setValue(this.task.name)
    this.taskForm.get('startDate').setValue(this.task.startDate)
    this.taskForm.get('endDate').setValue(this.task.endDate)
    this.taskForm.get('intervalUnit').setValue(this.task.intervalUnit)
    this.taskForm.get('intervalAmount').setValue(this.task.intervalAmount)
    this.taskForm.get('description').setValue(this.task.description)
    this.taskForm.get('assignees').setValue(this.task.assignees as unknown as string[])
    for (const question of this.task.questions) {
      this.addQuestion(question.name,question.type,question.options)
    }
  }
}
