import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmptyRouteComponent } from '../../empty-route/empty-route.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RecoveryPassComponent } from './pages/recovery-pass/recovery-pass.component';
import { LayoutComponent } from './pages/layout/layout.component';
import { InfoUserComponent } from './pages/recovery-pass/components/info-user/info-user.component';
import { CodeComponent } from './pages/recovery-pass/components/code/code.component';
import { canActivateRecovery } from './guards/recovery.guard';

const routes: Routes = [
  { path: "",
    component: LayoutComponent,
    children: [
        { component: LoginPageComponent, path: ""},
        { component: RecoveryPassComponent, 
          path: "recovery", 
          children: [
            { path: "", component: InfoUserComponent, pathMatch: "full"},
            { path: "code", component: CodeComponent, canActivate: [canActivateRecovery] },
          ]}
    ] 
  },
  { path: '**', component: EmptyRouteComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }