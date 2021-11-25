import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageSendReplyComponent } from './message-send-reply.component';

describe('MessageSendReplyComponent', () => {
  let component: MessageSendReplyComponent;
  let fixture: ComponentFixture<MessageSendReplyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessageSendReplyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MessageSendReplyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
