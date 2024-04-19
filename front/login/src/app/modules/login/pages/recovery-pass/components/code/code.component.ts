import { Component, ElementRef, HostListener, OnDestroy, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { UserInfo } from 'src/app/modules/auth/interfaces/recovery-response.interface';
import { RecoveryService } from 'src/app/modules/auth/services/recovery.service';
import { ModalService } from 'src/app/shared/services/modal.service';

@Component({
  selector: 'app-code',
  templateUrl: './code.component.html',
  styleUrls: ['./code.component.scss']
})
export class CodeComponent implements OnInit, OnDestroy  {

  @ViewChild("firstInput") firstInput! : ElementRef<HTMLElement>;

  @HostListener('paste', ['$event'])
  onClick(e: ClipboardEvent) {
    let text = e.clipboardData?.getData('text');
    if(text) this.pasteCode(text)
  }

  private recoveryService = inject(RecoveryService);
  private router = inject(Router);
  private modalService = inject(ModalService);
  private messageService = inject(MessageService);
  private fb = inject(FormBuilder);

  subscription!: Subscription;

  codeGroup = this.fb.group({
    code1: ["", Validators.required],
    code2: ["", Validators.required],
    code3: ["", Validators.required],
    code4: ["", Validators.required],
  })

  userInfo : UserInfo | null = null;

  loading = false;

  sendingCode = false;

  ngOnInit(): void {
    this.subscription = this.recoveryService.userInfo()
                              .subscribe(res => {
                                this.userInfo = res;
                              })
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

  onSubmit(){
    this.codeGroup.markAllAsTouched();
    if(this.codeGroup.invalid) return;
    this.confirmCode();
  }

  onKeyup(event : KeyboardEvent){
    if(!this.codeGroup.invalid) return this.onSubmit();
  
    let value = (event.target as HTMLInputElement).value;
    if(!value) return

    let element;

    if (event.key !== "Backspace"){
      element = (event.target as HTMLInputElement).nextElementSibling;
      (element as HTMLInputElement).focus();
    }
        
  }

  switchLoading(){
    this.loading = !this.loading;
  }
  switchSendingCode(){
    this.sendingCode = !this.sendingCode;
  }

  confirmCode(){
    this.switchLoading();
    const {code4, code1, code2, code3} = this.codeGroup.value;
    this.recoveryService.confirmCode( code1! + code2 + code3 + code4)
    .subscribe({
      next: () => {this.router.navigate(['/recovery/password'])},
      error: () => {
        this.switchLoading();
        this.clearForm();
        this.modalService.activeModal({
          message: "El código que ingresaste no es válido, por favor volvé a verificarlo en tu correo y reitentá, o podes volver a ingresar tus datos",
          title: "¡Ups!",
          icon: "pi pi-exclamation-circle",
          footer:{
            buttonExittext: "Reitentar",
            link: "/recovery",
            textLink: "Volver a ingresar mis datos",
          }
        })
      },
      complete: () => this.switchLoading()
    })
  }

  clearForm(){
    this.firstInput.nativeElement.focus();
    this.codeGroup.markAsUntouched()
    this.codeGroup.setValue({code1 : "", code2 : "", code3: "", code4: ""})
    this.codeGroup.clearValidators()
  }

  resendCode(){
    this.switchSendingCode();
    this.recoveryService.recovery(this.userInfo?.documentNumber!, this.userInfo?.tramitNumber!)
      .subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success', 
            summary: 'Email reenviado', 
            detail: 'Volvé a revisar tu correo',
            key: 'bc',
          })
        },
        error: () => this.switchSendingCode(),
        complete: () => this.switchSendingCode()
      })
  }

  formInvalid():boolean{
    return  this.codeGroup.invalid && 
            this.codeGroup.controls.code1.touched &&
            this.codeGroup.controls.code2.touched &&
            this.codeGroup.controls.code3.touched &&
            this.codeGroup.controls.code4.touched;
  }

  pasteCode(clipText: string){
        let splitedText = clipText.split("");
        if(splitedText.length < 4) return;
        this.codeGroup.setValue({ code1: splitedText[0],
                                  code2: splitedText[1],
                                  code3: splitedText[2],
                                  code4: splitedText[3],
         }) 
  }

}
