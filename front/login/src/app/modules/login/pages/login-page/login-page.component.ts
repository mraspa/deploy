import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ValidationService } from 'src/app/shared/services/validation.service';
import { navigateToUrl } from 'single-spa';
import { AuthService } from 'src/app/modules/auth/services/auth.service';
import { ModalService } from 'src/app/shared/services/modal.service';
import { delay } from 'rxjs';


@Component({
  selector: 'module-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {

  private fb = inject(FormBuilder);
  private modalService = inject(ModalService);

  private validatorService = inject(ValidationService);
  private authService = inject(AuthService);

  loading=false;

  isPassHidden = true;

  loginGroup = this.fb.group({
    email: ["", [Validators.required, Validators.pattern(this.validatorService.emailPattern)]],
    password: ["", [Validators.required]]
  })

  onSubmit(){
    this.loginGroup.markAllAsTouched();
    if(this.loginGroup.invalid || this.loading) return;
    const {email, password} = this.loginGroup.value;
    if(email && password){
      this.switchLoading();
      this.authService.login({email, password})
      .pipe(
        delay(2000)
      )
      .subscribe({
        next: () => {
          alert("logueado correctamente")
        },
        error: (err) => {
          setTimeout(() => {
            this.modalService.activeModal({
              message: "No pudimos comprobar tus credenciales.\n Volvé a intentarlo.",
              title: "¡Ups!",
              icon: "pi pi-exclamation-circle",
              footer:{
                buttonExittext: "Reintentar",
                link: "/recovery",
                textLink: "¿Problemas para ingresar a tu cuenta?"
              }
            })
            this.switchLoading();
          }, 2000)
          
        },
        complete: () => this.switchLoading()
      })
    }
    
  }

  switchPassShow(){
    this.isPassHidden = ! this.isPassHidden;
  }

  isInvalidField(field: string):boolean|null{
    return this.validatorService.isInvalidField(field, this.loginGroup)
  }

  switchLoading(){
    this.loading = !this.loading;
  }

  getErrorField(field : string):string{
    return this.validatorService.getErrorField(field, this.loginGroup);
  }

  navigate(url:string){
    navigateToUrl(url)
  }
}
