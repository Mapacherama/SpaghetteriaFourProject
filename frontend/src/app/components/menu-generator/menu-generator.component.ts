import {FormBuilder, FormGroup} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../_service/api.service';

@Component({
  selector: 'app-menu-generator',
  templateUrl: './menu-generator.component.html',
  styleUrls: ['./menu-generator.component.scss']
})
export class MenuGeneratorComponent implements OnInit {

  addMenuForm: FormGroup;

  meatDishes: any = [];
  veggieDishes: any = [];
  fishDishes: any = [];
  firstDish: any;
  secondDish: any;
  thirdDish: any;
  fourthDish: any;
  fifthDish: any;
  sixthDish: any;
  seventhDish: any;

  constructor(private apiService: ApiService, private formBuilder: FormBuilder) {
    this.addMenuForm = this.formBuilder.group({
      firstDish: '',
      secondDish: '',
      thirdDish: '',
      fourthDish: '',
      fifthDish: '',
      sixthDish: '',
      seventhDish: ''
    });
  }

  ngOnInit(): void {
    this.apiService.getAllProducts().then((data) => {
      for (const dish of data) {
        if (dish.disabled) continue;
        if (dish.type === "Vegetarisch") this.veggieDishes.push(dish);
        else if (dish.type === "Vis") this.fishDishes.push(dish);
        else if (dish.type === "Vlees") this.meatDishes.push(dish);
      }
    });
  }

  addMenu(): void {
    var dishes: any[] = [];

    Object.keys(this.addMenuForm.controls).forEach(key => {
      const dish = this.addMenuForm.controls[key].value;
      dishes.push({
        name: dish.productName,
        desc: dish.description,
        price: dish.price
      })
    });

    this.apiService.generateMenu(dishes).then(data => this.download(data, 'menu.xlsx', 'xlsx'));
  }

  download(data, filename, type) {
    var file = new Blob([data], {type: type});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
      window.navigator.msSaveOrOpenBlob(file, filename);
    else { // Others
      var a = document.createElement("a"),
        url = URL.createObjectURL(file);
      a.href = url;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      setTimeout(function() {
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      }, 0);
    }
  }

}

