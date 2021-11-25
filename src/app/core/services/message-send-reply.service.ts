import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {
  CandidateInterview, Interviewer,
  MessageSendReplyRequest,
  MessageSendReplyResponse,
  ResponseModel
} from "@app/core/model/core.model";
import {Observable} from "rxjs";
import {ApiEndpoints} from "@app/core/app-url.constant";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class MessageSendReplyService {

  constructor(private http: HttpClient) {
  }

  public sendMessage(messageSendReply: MessageSendReplyRequest): Observable<MessageSendReplyResponse> {
    const messageSendResponseObservable: Observable<MessageSendReplyResponse> = this.http
      .post<ResponseModel<MessageSendReplyResponse>>(ApiEndpoints.MESSAGE.SEND_MESSAGE, messageSendReply)
      .pipe(
        map((messageSendResponse: ResponseModel<MessageSendReplyResponse>) => {
          return messageSendResponse.object;
        })
      );
    return messageSendResponseObservable;
  }

  public replyMessage(messageSendReply: MessageSendReplyRequest): Observable<MessageSendReplyResponse> {
    const messageReplyResponseObservable: Observable<MessageSendReplyResponse> = this.http
      .post<ResponseModel<MessageSendReplyResponse>>(ApiEndpoints.MESSAGE.REPLY_MESSAGE, messageSendReply)
      .pipe(
        map((messageSendResponse: ResponseModel<MessageSendReplyResponse>) => {
          return messageSendResponse.object;
        })
      );
    return messageReplyResponseObservable;
  }

  public getAllMessagesByCandidateJobId(candidateJobId: number): Observable<MessageSendReplyResponse[]> {
    const messageSendReplyListObs: Observable<MessageSendReplyResponse[]> = this.http
      .get<ResponseModel<MessageSendReplyResponse[]>>(ApiEndpoints.MESSAGE.GET_ALL_MESSAGES_BY_CANDIDATE_JOB + '/' + candidateJobId)
      .pipe(
        map((messageResponseModel: ResponseModel<MessageSendReplyResponse[]>) => {
          return messageResponseModel.object;
        })
      );
    return messageSendReplyListObs;
  }
}
