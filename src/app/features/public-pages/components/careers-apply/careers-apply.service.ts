import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {ApiEndpoints} from '@app/core/app-url.constant';
import {Observable} from 'rxjs';
import {JobApplyRequest} from '@app/core/model/core.model';

@Injectable({
  providedIn: 'root'
})
export class CareersApplyService {
  readonly PUBLIC_URL = ApiEndpoints.PUBLIC_URL;

  constructor(private http: HttpClient) {
  }

  public applyForJob(jobApplyRequest: JobApplyRequest, resumeFile: Blob): Observable<any> {
    const formData: any = new FormData();
    // formData.append("file", document.forms[formName].file.files[0]);
    formData.append('file', resumeFile);
    formData.append('resumeRequest', new Blob([JSON.stringify(jobApplyRequest)], {
      type: 'application/json'
    }));

    return this.http.post(this.PUBLIC_URL.JOB_APPLY, formData);
  }

  public getAllJob(): Observable<any> {
    return this.http.get(this.PUBLIC_URL.JOBS_LIST);
  }
}
