declare module "*.html" {
  const rawHtmlFile: string;
  export = rawHtmlFile;
}

declare module "*.bmp" {
  const src: string;
  export default src;
}

declare module "*.gif" {
  const src: string;
  export default src;
}

declare module "*.jpg" {
  const src: string;
  export default src;
}

declare module "*.jpeg" {
  const src: string;
  export default src;
}

declare module "*.png" {
  const src: string;
  export default src;
}

declare module "*.webp" {
  const src: string;
  export default src;
}

declare module "*.svg" {
  const src: string;
  export default src;
}

declare module "@login/module"
declare module "@onboarding/module"
declare module "@lifestyle/module"
declare module "@header/module"
declare module "@footer/module"
declare module "@wallet/module"


declare module "@shared/jwt"{
  class Jwt {
      private _token;
      private readonly KEYTOKEN;
      constructor();
      getToken(): string;
      setToken(token: string): void;
      clearToken():void;
      private loadLocalStorage;
  }
  export const jwt: Jwt;
}