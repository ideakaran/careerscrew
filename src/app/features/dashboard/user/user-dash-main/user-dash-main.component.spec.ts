import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDashMainComponent } from './user-dash-main.component';

describe('UserDashMainComponent', () => {
  let component: UserDashMainComponent;
  let fixture: ComponentFixture<UserDashMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserDashMainComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDashMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
