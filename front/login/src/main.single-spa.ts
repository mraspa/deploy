import { enableProdMode, NgZone } from '@angular/core';

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { Router, NavigationStart } from '@angular/router';

import { singleSpaAngular, getSingleSpaExtraProviders } from 'single-spa-angular';


import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import { singleSpaPropsSubject } from './single-spa/single-spa-props';

import './config.prime'

if (environment.production) {
  enableProdMode();
}


(window as any).plattform = (window as any).plattform || {};
let platform = (window as any).plattform["16.2.0"];
if (!platform) {
  platform = platformBrowserDynamic(getSingleSpaExtraProviders());
  (window as any).plattform["16.2.0"] = platform; 
}


const lifecycles = singleSpaAngular({
  bootstrapFunction: singleSpaProps => {
    singleSpaPropsSubject.next(singleSpaProps);
    return platform.bootstrapModule(AppModule);
  },
  template: '<login-root />',
  Router,
  NavigationStart,
  NgZone,
});

export const bootstrap = lifecycles.bootstrap;
export const mount = lifecycles.mount;
export const unmount = lifecycles.unmount;
