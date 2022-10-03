import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ApiService} from '../../../../_service/api.service';

@Component({
  selector: 'app-create-announcement',
  templateUrl: './create-announcement.component.html',
  styleUrls: ['./create-announcement.component.scss']
})
export class CreateAnnouncementComponent implements OnInit {
  form: FormGroup;
  branches: string[];
  minDeadline: Date;

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router, private api: ApiService) {

  }

  ngOnInit(): void {
    this.minDeadline = new Date();
    this.minDeadline.setMinutes(0);
    this.minDeadline.setHours(this.minDeadline.getHours()+1)

    this.form = this.formBuilder.group({
      topic: '',
      description: '',
      startDate: new Date(this.minDeadline.valueOf()),
      endDate: new Date(this.minDeadline.valueOf() + (24 * 60 * 60 * 1000)),
      branches: ['']
    });

    this.getBranches();
  }

  getBranches(): void {
    this.api.getAllBranches().then((data) => {
      this.branches = [];
      for (const branch of data) {
        this.branches.push(branch.username);
      }
    });
  }

  onSubmit(): void {
    this.api.addAnnouncement(this.form.value).then(() => this.router.navigate(['/admin/panel']));
  }

}
