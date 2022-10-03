import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from '../../components/login/login.component';
import {HomeComponent} from '../../components/home/home.component';
import {SelectbranchComponent} from '../../components/selectbranch/selectbranch.component';
import {AuthGuardService} from '../../_service/auth-guard.service';
import {AdminGuardService} from '../../_service/admin-guard.service';
import {HomeLayoutComponent} from '../../layouts/home-layout/home-layout.component';
import {LoginLayoutComponent} from '../../layouts/login-layout/login-layout.component';
import {ViewTaskComponent} from '../../components/tasks/view-task/view-task.component';
import {AdminPanelComponent} from '../../components/admin/panel/admin-panel.component';
import {ViewContactComponent} from '../../components/view-contact/view-contact.component';
import {CreateTaskComponent} from '../../components/tasks/create-task/create-task.component';
import {CalenderComponent} from '../../components/tasks/calender/calender.component';
import {EditComponent} from '../../components/tasks/edit/edit.component';
import {CreateRecurringComponent} from '../../components/tasks/create-recurring/create-recurring.component';
import {ContactEditComponent} from '../../components/admin/contact/contact-edit/contact-edit.component';
import {ContactCreateComponent} from "../../components/admin/contact/contact-create/contact-create.component";
import {AddUserComponent} from "../../components/admin/add-user/add-user.component";
import {EditUserComponent} from "../../components/admin/edit-user/edit-user.component";
import {CreateDishComponent} from '../../components/admin/dish/create-dish/create-dish.component';
import {EditDishComponent} from '../../components/admin/dish/edit-dish/edit-dish.component';
import {CreateAnnouncementComponent} from '../../components/admin/announcement/create-announcement/create-announcement.component';
import {EditAnnouncementComponent} from '../../components/admin/announcement/edit-announcement/edit-announcement.component';
import {MenuGeneratorComponent} from '../../components/menu-generator/menu-generator.component';
import {BranchAnnouncementComponent} from '../../components/branch-announcements/branch-announcement.component';
import {EditRecurringComponent} from "../../components/edit-recurring/edit-recurring.component"; // CLI imports router

const routes: Routes = [
  {
    path: '',
    component: HomeLayoutComponent,
    canActivate: [AuthGuardService],
    children: [
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'taken',
        component: HomeComponent
      },
      {
        path: 'taak/aanmaken/recurring',
        component: CreateRecurringComponent
      },
      {
        path: 'taak/aanmaken',
        component: CreateTaskComponent
      },
      {
        path: 'taak/overview',
        component: CalenderComponent
      },
      {
        path: 'taak/bewerken/:task',
        component: EditComponent
      },
      {
        path: 'taak/:task',
        component: ViewTaskComponent
      },
      {
        path: 'admin/panel/:tab',
        component: AdminPanelComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/recurringtask/edit/:task',
        component: EditRecurringComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/panel',
        component: AdminPanelComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/contact/edit/:id',
        component: ContactEditComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/user/edit/:id',
        component: EditUserComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/user/add',
        component: AddUserComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/contact/add',
        component: ContactCreateComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/dish/edit/:id',
        component: EditDishComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/dish/add',
        component: CreateDishComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/announcement/add',
        component: CreateAnnouncementComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'admin/announcement/edit/:id',
        component: EditAnnouncementComponent,
        canActivate: [AdminGuardService]
      },
      {
        path: 'contacts',
        component: ViewContactComponent
      },
      {
        path: 'menu',
        component: MenuGeneratorComponent
      },
      {
        path: 'berichten',
        component: BranchAnnouncementComponent
      }
    ]
  },
  {
    path: '',
    component: LoginLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'selectbranch',
        component: SelectbranchComponent,
        canActivate: [AuthGuardService, AdminGuardService]
      }
    ]
  }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
