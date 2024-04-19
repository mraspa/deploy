import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalErrorComponent } from './components/modal/modal.component';
import { PrimeNGModule } from '../modules/prime-ng/prime-ng.module';
import { HeaderComponent } from './components/header/header.component';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [ModalErrorComponent, HeaderComponent],
  imports: [
    CommonModule,
    PrimeNGModule,
    RouterModule
  ],
  exports:[
    ModalErrorComponent,
    HeaderComponent
  ]
})
export class SharedModule { }
