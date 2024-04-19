// this config dynamically routes for prime

import { environment } from "./environments/environment";

let newStyle = document.createElement('style');

newStyle.appendChild(document.createTextNode(`

/* PrimeIcon css */
  @font-face {
    font-family: 'primeicons';
    font-display: block;
    src: url("${environment.localUrl}/primeicons.eot");
    src: url("${environment.localUrl}/primeicons.eot?#iefix") 
    format('embedded-opentype'),  url("${environment.localUrl}/primeicons.woff2") format('woff2'), 
                                  url("${environment.localUrl}/primeicons.woff") format('woff'), 
                                  url("${environment.localUrl}/primeicons.ttf") format('truetype'), 
                                  url("${environment.localUrl}/primeicons.svg?#primeicons") format('svg');
    font-weight: normal;
    font-style: normal;
  }
   
  `));

document.body.appendChild(newStyle);