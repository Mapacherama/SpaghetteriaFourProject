import {Component, OnInit} from '@angular/core';
import {FormGroup, FormControl, FormArray, FormBuilder, Validators} from '@angular/forms'
import {ApiService} from '../../../_service/api.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.scss']
})
export class CreateTaskComponent implements OnInit {
  isAdmin: boolean;
  taskForm: FormGroup;
  questionsArray: FormArray;
  minDeadline: Date;
  branches: string[];


  constructor(private fb: FormBuilder, private apiService: ApiService, private router:Router) {

  }

  ngOnInit(): void {
    this.isAdmin = sessionStorage.getItem('role') === 'ADMIN';
    this.minDeadline = new Date();
    this.minDeadline.setTime(this.minDeadline.getTime() + (60 * 60 * 1000));
    this.minDeadline.setMinutes(0);
    this.minDeadline.setHours(this.minDeadline.getHours()+1)

    if (this.isAdmin) {
      this.getBranches()
      this.taskForm = this.fb.group({
        name: '',
        deadline: '',
        description: '',
        assignees: '',
        submittedBy: this.fb.array([]),
        questions: this.fb.array([this.newQuestion('textfield')])
      });
    } else {
      this.branches = [sessionStorage.getItem('branch')]
      this.taskForm = this.fb.group({
        name: '',
        deadline: '',
        description: '',
        assignees: this.branches[0],
        submittedBy: this.fb.array([]),
        questions: this.fb.array([this.newQuestion('textfield')])
      });
    }
  }

  newQuestion(t: string): FormGroup {
    return this.fb.group({
      name: '',
      type: t,
      options: ''
    });
  }

  addQuestion(t: string) {
    this.questionsArray = this.taskForm.get('questions') as FormArray;
    this.questionsArray.push(this.newQuestion(t));
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
      this.apiService.createTask(body).then((data) => {
        // @ts-ignore
        if (data.name === body.name) {
          alert('Succesvol toegevoegd!')
          this.resetForm();
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
    this.router.navigateByUrl('/taak/aanmaken/recurring', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/taak/aanmaken']);
    });
  }

  test(item: any) {
    console.log(item)
  }
}
