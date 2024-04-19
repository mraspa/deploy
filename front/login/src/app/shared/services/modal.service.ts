import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ModalInfo } from '../interfaces/modal.interface';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private _state = new Subject<ModalInfo>();


  activeModal( modalInfo : ModalInfo){
    this._state.next(modalInfo);
  }

  get visible():Observable<ModalInfo>{
    return this._state.asObservable();
  }
}
