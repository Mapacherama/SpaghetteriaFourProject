import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HomeComponent } from './components/home/home.component';
import {AppRoutingModule} from "./_modules/app-routing/app-routing.module";
import { SelectbranchComponent } from './components/selectbranch/selectbranch.component';
import {TokenInterceptor} from "./_service/token-interceptor.service";
import { MenuComponent } from './components/menu/menu.component';
import { HomeLayoutComponent } from './layouts/home-layout/home-layout.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';
import { ViewTaskComponent } from './components/tasks/view-task/view-task.component';
import { AdminPanelComponent } from './components/admin/panel/admin-panel.component';
import { AddUserComponent } from './components/admin/add-user/add-user.component';
import {ViewContactComponent} from './components/view-contact/view-contact.component';
import { CreateTaskComponent } from './components/tasks/create-task/create-task.component';
import {OWL_DATE_TIME_LOCALE, OwlDateTimeModule, OwlNativeDateTimeModule} from 'ng-pick-datetime';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {SelectDropDownModule} from "ngx-select-dropdown";
import { CalenderComponent } from './components/tasks/calender/calender.component';
import { EditComponent } from './components/tasks/edit/edit.component';
import { CreateRecurringComponent } from './components/tasks/create-recurring/create-recurring.component';
import { ContactOverviewComponent } from './components/admin/contact/contact-overview/contact-overview.component';
import {DTableModule} from "ngx-d-table";
import { ContactEditComponent } from './components/admin/contact/contact-edit/contact-edit.component';
import { ContactCreateComponent } from './components/admin/contact/contact-create/contact-create.component';
import { UserManagmentComponent } from './components/admin/user-managment/user-managment.component';
import { EditUserComponent } from './components/admin/edit-user/edit-user.component';
import { MenuGeneratorComponent } from './components/menu-generator/menu-generator.component';
import { EditDishComponent } from './components/admin/dish/edit-dish/edit-dish.component';
import { CreateDishComponent } from './components/admin/dish/create-dish/create-dish.component';
import { DishOverviewComponent } from './components/admin/dish/dish-overview/dish-overview.component';
import { ReportOverviewComponent } from './components/admin/statistics/report-overview/report-overview.component';
import { TasksOverviewComponent } from './components/admin/statistics/tasks-overview/tasks-overview.component';
import { AdminHomeComponent } from "./components/admin/home/admin-home.component";
import { CreateAnnouncementComponent } from './components/admin/announcement/create-announcement/create-announcement.component';
import { AnnouncementOverviewComponent } from './components/admin/announcement/announcement-overview/announcement-overview.component';
import { TaskOverviewComponent } from './components/admin/task/task-overview/task-overview.component';
import { RecurringTaskEditComponent } from './components/admin/task/recurring-task-edit/recurring-task-edit.component';
import { BranchAnnouncementComponent } from './components/branch-announcements/branch-announcement.component';
import { EditRecurringComponent } from './components/edit-recurring/edit-recurring.component';
import { EditAnnouncementComponent } from './components/admin/announcement/edit-announcement/edit-announcement.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SelectbranchComponent,
    MenuComponent,
    HomeLayoutComponent,
    LoginLayoutComponent,
    ViewTaskComponent,
    AdminPanelComponent,
    AddUserComponent,
    ViewContactComponent,
    CreateTaskComponent,
    CalenderComponent,
    EditComponent,
    CreateRecurringComponent,
    ContactOverviewComponent,
    ContactEditComponent,
    ContactCreateComponent,
    UserManagmentComponent,
    EditUserComponent,
    MenuGeneratorComponent,
    EditDishComponent,
    CreateDishComponent,
    DishOverviewComponent,
    CreateAnnouncementComponent,
    AnnouncementOverviewComponent,
    TaskOverviewComponent,
    RecurringTaskEditComponent,
    BranchAnnouncementComponent,
    ReportOverviewComponent,
    TasksOverviewComponent,
    AdminHomeComponent,
    EditRecurringComponent,
    EditAnnouncementComponent,
  ],
  imports: [
    BrowserModule,
    NoopAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    SelectDropDownModule,
    DTableModule,
    FormsModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }, {provide: OWL_DATE_TIME_LOCALE, useValue: 'nl'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
