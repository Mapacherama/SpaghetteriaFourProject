import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ApiService} from "../../../../_service/api.service";

@Component({
  selector: 'app-edit-dish',
  templateUrl: './edit-dish.component.html',
  styleUrls: ['./edit-dish.component.scss']
})
export class EditDishComponent implements OnInit {
  @Input() dishId: number;
  form: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router, private api: ApiService) {

  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      id: this.dishId,
      productName: '',
      description: '',
      price: '',
      type: '',
      disabled: ''
    });

    if (this.dishId === undefined && this.activatedRoute.snapshot.params.id !== undefined) {
      this.dishId = this.activatedRoute.snapshot.params.id;
    }
    if (this.dishId === undefined) {
      this.router.navigate(['/admin/panel/1']);
      return;
    }

    this.api.getProductById(this.dishId).then((data) => {
      this.form = this.formBuilder.group({
        id: data.id,
        productName: data.productName,
        description: data.description,
        price: data.price,
        type: data.type,
        disabled: data.disabled
      });
    })
  }

  onSubmit() {
    this.api.updateProduct(this.form.value).then((data) => {
      alert('Succesvol aangepast!')
      this.router.navigate(['/admin/panel/1'])
    })
  }

  removeDish() {
    this.api.deleteProduct(this.dishId).then((data) => {
      if (!data) {
        alert('Succesvol verwijderd!');
        this.router.navigate(['/admin/panel/1']);
      } else {
        alert('Oeps, er is iets misgegaan!');
      }
    })
  }
}
