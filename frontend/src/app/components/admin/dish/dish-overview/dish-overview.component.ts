import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../../../_service/api.service";
import {Column} from "ngx-d-table";

@Component({
  selector: 'app-dish-overview',
  templateUrl: './dish-overview.component.html',
  styleUrls: ['./dish-overview.component.scss']
})
export class DishOverviewComponent implements OnInit {
  dishes: any[];
  columns  = [
    { name: 'productName', title: 'Naam', format: '' } as Column,
    { name: 'description', title: 'Beschrijving', format: '' } as Column,
    { name: 'price', title: 'Prijs', format: '' } as Column,
    { name: 'type', title: 'Soort', format: '' } as Column,
    { name: 'disabled', title: 'Uitgeschakeld', format: 'Boolean' } as Column,
  ];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.api.getAllProducts().then((data) => this.dishes = data);
  }

}
