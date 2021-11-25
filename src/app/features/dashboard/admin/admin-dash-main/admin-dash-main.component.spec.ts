import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDashMainComponent } from './admin-dash-main.component';

describe('AdminDashMainComponent', () => {
  let component: AdminDashMainComponent;
  let fixture: ComponentFixture<AdminDashMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDashMainComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDashMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
