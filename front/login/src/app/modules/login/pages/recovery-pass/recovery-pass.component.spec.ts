import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecoveryPassComponent } from './recovery-pass.component';

describe('RecoveryPassComponent', () => {
  let component: RecoveryPassComponent;
  let fixture: ComponentFixture<RecoveryPassComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecoveryPassComponent]
    });
    fixture = TestBed.createComponent(RecoveryPassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
