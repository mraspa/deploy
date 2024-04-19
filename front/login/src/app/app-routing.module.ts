import { APP_BASE_HREF } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmptyRouteComponent } from './empty-route/empty-route.component';

const routes: Routes = [
  { 
    path: "", 
    loadChildren: () => import('./modules/login/login.module').then(m => m.LoginModule) },
  { path: '**', component: EmptyRouteComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  providers: [{ provide: APP_BASE_HREF, useValue: '/auth/login/' }],
  exports: [RouterModule]
})
export class AppRoutingModule { }
