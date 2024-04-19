import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  private _emailPattern: RegExp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  get emailPattern(){
    return this._emailPattern;
  }

  isInvalidField(field : string, form : FormGroup):boolean|null{
    return form.controls[field].errors
            && form.controls[field].touched
  }


  getErrorField(field : string, formGroup: FormGroup):string{
    let errors = formGroup.controls[field].errors;
    let keyError = Object.keys(errors ?? {})[0]

    if(!errors) return "";
     switch(keyError){
        case "required":
         return '*campo requerido';
        case "pattern":
         return "El email debe ser en un formato correcto";
        case "minlength":
          return `El mínimo requerido es de ${errors[keyError].requiredLength}`;
        case "maxlength":
          return `El máximo requerido es de ${errors[keyError].requiredLength}`;
       default:
         return "Some error"
     }

 }
}
