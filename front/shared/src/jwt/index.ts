
class Jwt{

    private _token : string = "";

    private readonly KEYTOKEN = "KEYTOKEN";

    constructor(){
        this.loadLocalStorage();
    }

    getToken():string{
        return this._token;
    }
   
    setToken(token : string){
        this._token = token;
        this.saveToken();
    }

    private loadLocalStorage(){
        let token = localStorage.getItem(this.KEYTOKEN);
        if(token) this._token = token;
    }

    private saveToken(){
        localStorage.setItem(this.KEYTOKEN, this._token);
    }


}

export const jwt = new Jwt();