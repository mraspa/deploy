import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { LoginRoutingModule } from './login-routing.module';
import { PrimeNGModule } from '../prime-ng/prime-ng.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
import { RecoveryPassComponent } from './pages/recovery-pass/recovery-pass.component';
import { LayoutComponent } from './pages/layout/layout.component';
import { InfoUserComponent } from './pages/recovery-pass/components/info-user/info-user.component';
import { CodeComponent } from './pages/recovery-pass/components/code/code.component';



@NgModule({
  declarations: [LoginPageComponent, RecoveryPassComponent, LayoutComponent, InfoUserComponent, CodeComponent],
  imports: [
    CommonModule,
    LoginRoutingModule,
    PrimeNGModule,
    ReactiveFormsModule,
    SharedModule,
    FormsModule
  ]
})
export class LoginModule { }
