import { navigateToUrl, registerApplication, start } from "single-spa";

import {
  constructApplications,
  constructRoutes,
  constructLayoutEngine
} from "single-spa-layout";

import microfrontendLayout from "./microfrontend-layout.html";
import { jwt } from "@shared/jwt";


const routes = constructRoutes(microfrontendLayout);

const applications = constructApplications({
  routes,
  loadApp({ name }) {
    
    if(name === "@wallet/wallet" || name === "@wallet/header" || name === "@wallet/footer"){
      let token = localStorage.getItem("KEYTOKEN");
      if(!token) location.href ='http://localhost:9000/auth/login';

    }
    const moduleMap = {
      "@wallet/login": () => import("@login/module"),
      "@wallet/onboarding": () => import("@onboarding/module"),
      "@wallet/lifestyle": () => import("@lifestyle/module"),
      "@wallet/wallet": () => import("@wallet/module"),
      "@wallet/footer": () => import("@footer/module"),
      "@wallet/header": () => import("@header/module"),
    }
    return moduleMap[name]();
  },
});

const layoutEngine = constructLayoutEngine({ routes, applications });

applications.forEach(registerApplication);
layoutEngine.activate();
start();






// registerApplication({
//   name: "@single-spa/welcome",
//   app: () =>
//     System.import<LifeCycles>(
//       "https://unpkg.com/single-spa-welcome/dist/single-spa-welcome.js"
//     ),
//   activeWhen: ["/"],
// });
/*registerApplication({
  name: "@wallet/login",
  app: () =>
    System.import<LifeCycles>(
      "https://localhost:4200/main.js"
    ),
  activeWhen: ["login"],
});

registerApplication({
  name: "@wallet/onboarding",
  app: () =>
    System.import<LifeCycles>(
      "http://localhost:4201/main.js"
    ),
  activeWhen: ["onboarding"],
});

registerApplication({
  name: "@wallet/home",
  app: () =>
    System.import<LifeCycles>(
      "http://localhost:4202/main.js"
    ),
  activeWhen: ["home"],
});

registerApplication({
  name: "@wallet/shared",
  app: () =>
    System.import<LifeCycles>(
      "https://localhost:4203/main.js"
    ),
  activeWhen: [""],
});

start({
  urlRerouteOnly: true,
});*/




