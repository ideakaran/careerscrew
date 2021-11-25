import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDetailsWrapperComponent } from './user-details-wrapper.component';

describe('UserDetailsWrapperComponent', () => {
  let component: UserDetailsWrapperComponent;
  let fixture: ComponentFixture<UserDetailsWrapperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserDetailsWrapperComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDetailsWrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
