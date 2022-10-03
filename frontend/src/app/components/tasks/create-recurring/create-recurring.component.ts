import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {ApiService} from "../../../_service/api.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-recurring',
  templateUrl: './create-recurring.component.html',
  styleUrls: ['./create-recurring.component.scss']
})
export class CreateRecurringComponent implements OnInit {
  isAdmin: boolean;
  taskForm: FormGroup;
  questionsArray: FormArray;
  minDeadline: Date;
  branches: string[];


  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router) {

  }

  ngOnInit(): void {
    this.isAdmin = sessionStorage.getItem('role') === 'ADMIN';

    this.minDeadline = new Date();
    this.minDeadline.setMinutes(0);
    this.minDeadline.setHours(this.minDeadline.getHours()+1)
    this.getBranches()

    this.taskForm = this.fb.group({
      name: '',
      startDate: new Date(this.minDeadline.valueOf()),
      endDate: new Date(this.minDeadline.valueOf() + (24 * 60 * 60 * 1000)),
      intervalUnit: 'WEEKS',
      intervalAmount: 1,
      description: '',
      assignees: '',
      questions: this.fb.array([this.newQuestion('textfield')])
    });
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
      this.apiService.createRecurringTask(body).then((data) => {
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
    this.router.navigateByUrl('/taak/aanmaken', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/taak/aanmaken/recurring']);
    });
  }

  test(item: any) {
    console.log(item)
  }
}
