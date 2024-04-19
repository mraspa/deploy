import { Component, OnInit, inject } from '@angular/core';
import { ModalService } from '../../services/modal.service';
import { ModalInfo } from '../../interfaces/modal.interface';
import { assetUrl } from 'src/single-spa/asset-url';

@Component({
  selector: 'shared-modal-error',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalErrorComponent implements OnInit{

  modalInfo : ModalInfo = {} as ModalInfo;

  visible = false;

  private modalService = inject(ModalService);

  ngOnInit(): void {
    this.modalService.visible.subscribe({
      next: modalInfo => {
        this.modalInfo = modalInfo
        this.visible = true;
      }
    })
  }

  setAsseturl(img: string):string{
    return assetUrl(img)
  }

}
