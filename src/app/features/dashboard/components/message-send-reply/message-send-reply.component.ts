import {Component, Input, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {CandidateJob, MessageSendReplyRequest, MessageSendReplyResponse, User} from '@app/core/model/core.model';
import {MessageSendReplyService} from '@app/core/services/message-send-reply.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {CredentialsService} from '@app/auth/services/credentials.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-message-send-reply',
  templateUrl: './message-send-reply.component.html',
  styleUrls: ['./message-send-reply.component.scss']
})
export class MessageSendReplyComponent implements OnInit, OnDestroy {

  @Input() candidateJob: CandidateJob;

  isLoading = false;
  responseMessage: string;
  hasError = false;

  candidateJobMessageList: MessageSendReplyResponse[] = [];

  quillReplyMsg: any;
  quillInitialMsg: any;
  isNewClicked = false;
  quillNewInitialMsg: any;

  @ViewChild('askQueriesContent', {read: TemplateRef, static: true}) askQueriesContent!: TemplateRef<any>;
  private unsubscribe = new Subject<void>();

  constructor(private messageSendReplyService: MessageSendReplyService,
              private credentialService: CredentialsService,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.getAllCandidateMessages();
  }

  getAllCandidateMessages(): void {
    this.isNewClicked = false;
    this.candidateJobMessageList = [];
    this.isLoading = true;
    this.messageSendReplyService.getAllMessagesByCandidateJobId(this.candidateJob.id)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((messageList) => {
        this.candidateJobMessageList = messageList;
        this.isLoading = false;
        this.hasError = false;
      }, error => {
        this.hasError = false;
        this.isLoading = false;
      });
  }

  sendMessage(isNew = false): void {
    const messageSendRequest: MessageSendReplyRequest = {
      candidateJobId: this.candidateJob.id,
      initialMsg: isNew ? this.quillNewInitialMsg : this.quillInitialMsg,
    };
    this.isLoading = true;
    this.messageSendReplyService.sendMessage(messageSendRequest)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((value) => {
        this.responseMessage = 'Successfully send';
        this.isLoading = false;
        this.hasError = false;
        this.getAllCandidateMessages();
      }, error => {
        this.isLoading = false;
        this.hasError = true;
        this.responseMessage = 'Something went wrong while sending';
      });
  }

  replyMessage(id: number): void {
    const messageReplyRequest: MessageSendReplyRequest = {
      id,
      candidateJobId: this.candidateJob.id,
      replyMsg: this.quillReplyMsg,
    };
    this.isLoading = true;
    this.messageSendReplyService.replyMessage(messageReplyRequest)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((value) => {
        this.responseMessage = 'Your reply msg has been successfully send';
        this.isLoading = false;
        this.hasError = false;
      }, error => {
        this.isLoading = false;
        this.hasError = true;
        this.responseMessage = 'Something went wrong while replying';
      });
  }

  isCurrentUser(user: User): boolean {
    const credentials = this.credentialService.getCredentials();
    const id = credentials?.userEntity?.id;
    return user?.id === id;
  }

  openAskQueriesContent(): void {
    this.modalService.open(this.askQueriesContent, {size: 'lg', centered: true, keyboard: true}).result.then((result) => {
      // .close()
    }, (reason) => {
      // .dismiss() - cross or escape
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

}
