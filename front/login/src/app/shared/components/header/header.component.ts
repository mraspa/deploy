import { Component, Input, inject } from '@angular/core';
import { Location } from '@angular/common'
import { Router } from '@angular/router';

@Component({
  selector: 'shared-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  @Input()
  isBackPageActive = false;

  @Input()
  backPageUrl = "";

  private location = inject(Location);
  private router = inject(Router)

  goBackToPrevPage():void{
    if(this.backPageUrl) this.router.navigate([this.backPageUrl])
    else this.location.back();
  }



}
