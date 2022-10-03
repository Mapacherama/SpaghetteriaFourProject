import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ApiService} from "../../../../_service/api.service";

@Component({
  selector: 'app-create-dish',
  templateUrl: './create-dish.component.html',
  styleUrls: ['./create-dish.component.scss']
})
export class CreateDishComponent implements OnInit {
  form: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router, private api: ApiService) {

  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      productName: '',
      description: '',
      price: '',
      type: '',
      disabled: ''
    });
  }

  onSubmit() {
    this.api.createProduct(this.form.value).then((data) => {
      alert('Succesvol toegevoegd!')
      this.router.navigate(['/admin/panel'])
    })
  }
}
