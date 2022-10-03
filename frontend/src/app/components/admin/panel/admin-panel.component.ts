import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-adminpanel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit {
  selectedTab: number = 0;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

  }

}
