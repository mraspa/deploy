declare module "shared/jwt"{
    class Jwt {
        private _token;
        private readonly KEYTOKEN;
        constructor();
        getToken(): string;
        setToken(token: string): void;
        private loadLocalStorage;
    }
    export const jwt: Jwt;
}