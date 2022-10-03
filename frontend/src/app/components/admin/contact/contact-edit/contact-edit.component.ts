import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ApiService} from '../../../../_service/api.service';

@Component({
  selector: 'app-contact-edit',
  templateUrl: './contact-edit.component.html',
  styleUrls: ['./contact-edit.component.scss']
})
export class ContactEditComponent implements OnInit {
  @Input() contactId: number;
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

    if (this.contactId === undefined && this.activatedRoute.snapshot.params.id !== undefined) {
      this.contactId = this.activatedRoute.snapshot.params.id;
    }
    if (this.contactId === undefined) {
      this.router.navigate(['/admin/panel']);
      return;
    }

    this.getBranches();

    this.api.getDetailedContact(this.contactId).then((contact) => {
      this.contactForm = this.formBuilder.group({
        id: contact.id,
        companyName: contact.companyName,
        contact: contact.contact,
        phone: contact.phone,
        email: contact.email,
        branches: []
      });
      this.contactForm.get('branches').setValue(contact.branches as unknown as string[]);
    });

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
    this.api.updateContact(this.contactForm.value).then(() => this.router.navigate(['/admin/panel']));
  }

  removeContact(): void {
    if (confirm("Weet je het zeker?")) {
      this.api.deleteContact(this.contactId).then((data) => {
        if (data === true) {
          this.router.navigate(['/admin/panel'])
        } else alert('Er is iets fout gegaan!')
      })
    }
  }
}
