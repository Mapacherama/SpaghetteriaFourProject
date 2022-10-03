import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../_service/authentication.service";
import {ApiService} from "../../_service/api.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-selectbranch',
  templateUrl: './selectbranch.component.html',
  styleUrls: ['./selectbranch.component.scss']
})
export class SelectbranchComponent implements OnInit {
  branches: [];
  branchForm: FormGroup;

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router) { }

  ngOnInit(): void {
    this.getAllBranches()
    this.branchForm = this.fb.group({
      branch: ['']
    });
  }

  getAllBranches() {
    this.apiService.getAllBranches().then((data) => {
      this.branches = data
    });
  }

  setBranch() {
    sessionStorage.setItem('branch', this.branchForm.get('branch').value)
    this.router.navigate(['/home'])
  }
}
