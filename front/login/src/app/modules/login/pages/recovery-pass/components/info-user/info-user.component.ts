import { Component, EventEmitter, Output, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RecoveryService } from 'src/app/modules/auth/services/recovery.service';
import { ModalService } from 'src/app/shared/services/modal.service';
import { ValidationService } from 'src/app/shared/services/validation.service';

@Component({
  selector: 'recovery-info-user',
  templateUrl: './info-user.component.html',
  styleUrls: ['./info-user.component.scss']
})
export class InfoUserComponent {

  loading = false;

  private modalService = inject(ModalService)
  private fb = inject(FormBuilder);
  private recoveryService = inject(RecoveryService);
  private validationService = inject(ValidationService);
  private router = inject(Router);


  @Output()
  nextStep = new EventEmitter();

  recoveryGroup = this.fb.group({
    documentNumber: ["", [Validators.required, Validators.maxLength(8), Validators.minLength(8)]],
    tramitNumber: ["", [Validators.required, Validators.maxLength(11), Validators.minLength(11)]]
  })

  onSubmit(){
    this.recoveryGroup.markAllAsTouched();
    if(this.recoveryGroup.invalid) return;
    this.switchLoading();
    const {documentNumber, tramitNumber } = this.recoveryGroup.value;
    this.recoveryService.recovery(documentNumber!, tramitNumber!)
        .subscribe({
          next: () => {
            this.router.navigate(['/recovery/code'])
            this.switchLoading();
          },
          error: () => this.switchLoading(),
          complete: () => this.switchLoading()
        })
  }

  isInvalidField(field: string):boolean | null{
    return this.validationService.isInvalidField(field, this.recoveryGroup)
  }

  getErrorField(field:string):string{
    return this.validationService.getErrorField(field, this.recoveryGroup);
  }

  showInfo():void{
    this.modalService.activeModal({
      message: "Tal y como figura en la imagen, en el frente de tu Documento Nacional de Identidad encontrarás el número de 11 dígitos",
      title: "¿Cuál es mi número de trámite?",
      img: "/images/tramitNumber.png"
    })
  }

  switchLoading(){
    this.loading = !this.loading;
  }
}
