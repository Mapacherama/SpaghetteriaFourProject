import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../../../_service/api.service';

@Component({
  selector: 'app-recurring-task-edit',
  templateUrl: './recurring-task-edit.component.html',
  styleUrls: ['./recurring-task-edit.component.scss']
})
export class RecurringTaskEditComponent implements OnInit {


  constructor(private api: ApiService) {
  }

  ngOnInit(): void {

  }

}
