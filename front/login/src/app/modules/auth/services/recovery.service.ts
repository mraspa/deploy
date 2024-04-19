import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RecoveryResponse, UserInfo } from '../interfaces/recovery-response.interface';

@Injectable({
  providedIn: 'root'
})
export class RecoveryService {

  private readonly baseUrl = environment.apiGateaway + "/v1/password"

  private http = inject(HttpClient);

  private _userInfo = new BehaviorSubject<UserInfo | null>(null);
  
  userInfo():Observable<UserInfo | null>{
    return this._userInfo.asObservable();
  }

  RecoveryActived():boolean{
    return !!this._userInfo.value?.maskedMail;
  }

  recovery(documentNumber: string, tramitNumber: string):Observable<RecoveryResponse>{
    return this.http.post<RecoveryResponse>(`${this.baseUrl}/recovery`, {documentNumber, tramitNumber})
                      .pipe(
                        tap(res => {
                          this._userInfo.next({documentNumber, tramitNumber,  maskedMail: res.maskedMail}) 
                        })
                      )
  }

  confirmCode(code : string):Observable<any>{
    if(this._userInfo.value){
      const {documentNumber, tramitNumber} = this._userInfo.value;
      return this.http.post<any>(`${this.baseUrl}/validate-code`, {documentNumber, tramitNumber, code})
    } else {
      throw throwError(() => "error");  
    }
  }

}
