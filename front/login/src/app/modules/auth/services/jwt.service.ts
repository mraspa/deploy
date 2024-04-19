import { Injectable } from '@angular/core';
import { jwt } from '@shared/jwt';
@Injectable({
  providedIn: 'root'
})
export class JwtService {

  getToken(){
    jwt.getToken();
  }

  setToken(token : string){
    jwt.setToken(token)
  }
}
