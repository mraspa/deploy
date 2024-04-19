import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { SidebarModule } from 'primeng/sidebar';
import { KeyFilterModule } from 'primeng/keyfilter';
import { InputOtpModule } from 'primeng/inputotp';
import { ToastModule } from 'primeng/toast';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  exports: [
    ButtonModule,
    DividerModule,
    InputTextModule,
    PasswordModule,
    SidebarModule,
    KeyFilterModule,
    InputOtpModule,
    ToastModule,
  ]
})
export class PrimeNGModule { }
