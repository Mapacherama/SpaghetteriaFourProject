import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ApiService} from "../../../../_service/api.service";

@Component({
  selector: 'app-contact-create',
  templateUrl: './contact-create.component.html',
  styleUrls: ['./contact-create.component.scss']
})
export class ContactCreateComponent implements OnInit {
  contactForm: FormGroup;
  branches: string[];

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router, private api: ApiService) {

  }

  ngOnInit(): void {
    this.contactForm = this.formBuilder.group({
      companyName: '',
      contact: '',
      phone: '',
      email: '',
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
    this.api.addContact(this.contactForm.value).then(() => this.router.navigate(['/admin/panel']));
  }
}
