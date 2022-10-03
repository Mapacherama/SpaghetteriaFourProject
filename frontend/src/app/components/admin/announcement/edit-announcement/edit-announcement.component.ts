import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiService} from '../../../../_service/api.service';

@Component({
  selector: 'app-edit-announcement',
  templateUrl: './edit-announcement.component.html',
  styleUrls: ['./edit-announcement.component.scss']
})
export class EditAnnouncementComponent implements OnInit {
  @Input() announcementId: number;
  form: FormGroup;
  branches: string[];
  minDeadline: Date;

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router, private api: ApiService) {

  }

  ngOnInit(): void {
    this.minDeadline = new Date();
    this.minDeadline.setMinutes(0);
    this.minDeadline.setHours(this.minDeadline.getHours() + 1);

    this.form = this.formBuilder.group({
      topic: '',
      description: '',
      startDate: new Date(this.minDeadline.valueOf()),
      endDate: new Date(this.minDeadline.valueOf() + (24 * 60 * 60 * 1000)),
      branches: ['']
    });

    if (this.announcementId === undefined && this.activatedRoute.snapshot.params.id !== undefined) {
      this.announcementId = this.activatedRoute.snapshot.params.id;
    }

    if (this.announcementId === undefined) {
      this.router.navigate(['/admin/panel']);
      return;
    }

    this.getBranches();

    this.api.getDetailedAnnouncement(this.announcementId).then((announcement) => {
      this.form = this.formBuilder.group({
        id: announcement.id,
        topic: announcement.topic,
        description: announcement.description,
        startDate: announcement.startDate,
        endDate: announcement.endDate,
        branches: []
      });
      this.form.get('branches').setValue(announcement.branches as unknown as string[]);
    });
  }

  onSubmit(): void {
    this.api.updateAnnouncement(this.form.value).then((data) => {
      if (data) {
        this.router.navigate(['/admin/panel']);
      } else {
        alert('Er is iets fout gegaan!');
      }
    });
  }

  removeAnnouncement(): void {
    if (confirm('Weet je het zeker?')) {
      this.api.deleteAnnouncement(this.announcementId).then((data) => {
        if (data) {
          this.router.navigate(['/admin/panel']);
        } else {
          alert('Er is iets fout gegaan!');
        }
      });
    }
  }

  getBranches(): void {
    this.api.getAllBranches().then((data) => {
      this.branches = [];
      for (const branch of data) {
        this.branches.push(branch.username);
      }
    });
  }

}
