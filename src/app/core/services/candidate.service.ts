import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {
  CandidateAssessment,
  CandidateFile,
  CandidateInterview,
  CandidateJob,
  CandidateProfile,
  CandidateRemark,
  Interviewer,
  ResponseModel
} from '@app/core/model/core.model';
import {ApiEndpoints} from '@app/core/app-url.constant';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  constructor(private http: HttpClient) {
  }

  public getAllCandidateJobs(): Observable<CandidateJob[]> {
    const allCandidateJobsObservable: Observable<CandidateJob[]> = this.http
      .get<CandidateJob[]>(ApiEndpoints.CANDIDATE_JOBS.ALL_CANDIDATE_JOB_LISTING);
    return allCandidateJobsObservable;
  }

  public getAllInterviewers(): Observable<Interviewer[]> {
    const interviewersObservable: Observable<Interviewer[]> = this.http
      .get<ResponseModel<Interviewer[]>>(ApiEndpoints.CANDIDATE_JOBS.ALL_INTERVIEWERS_LISTING)
      .pipe(
        map((interviewers: ResponseModel<Interviewer[]>) => {
          return interviewers.object;
        })
      );
    return interviewersObservable;
  }

  public getCandidateCurrentJobByUserId(userId: number): Observable<CandidateJob> {
    const candidateJobObservable: Observable<CandidateJob> = this.http
      .get<CandidateJob>(ApiEndpoints.CANDIDATE_JOBS.CANDIDATE_CURRENT_JOB_BY_USER_ID + '/' + userId);
    return candidateJobObservable;
  }

  public getCandidateResumeByJobId(candidateJobId: number): Observable<CandidateFile> {
    const candidateFileObservable: Observable<CandidateFile> = this.http
      .get<CandidateFile>(ApiEndpoints.CANDIDATE_FILE.GET_CANDIDATE_RESUME_BY_CANDIDATE_JOB_ID + '/' + candidateJobId);
    return candidateFileObservable;
  }

  public downloadCandidateFileByFileId(candidateFileId: number): Observable<File> {
    const httpOptions = {
      responseType: 'blob' as 'json',
      headers: new HttpHeaders({
        responseType: 'blob'
      }),
    };
    return this.http.get<any>(ApiEndpoints.CANDIDATE_FILE.DOWNLOAD_CANDIDATE_FILE_BY_ID + '/' + candidateFileId, httpOptions);
  }

  // Candidate Interview, Profile and Remark
  public getCandidateInterviewByCandidateJobId(candidateJobId): Observable<CandidateInterview> {
    const candidateInterviewObservable: Observable<CandidateInterview> = this.http
      .get<ResponseModel<CandidateInterview>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_INTERVIEW + '/' + candidateJobId)
      .pipe(
        map((resCandidateInterview: ResponseModel<CandidateInterview>) => {
          return resCandidateInterview.object;
        })
      );
    return candidateInterviewObservable;
  }

  public getCandidateProfileByCandidateJobId(candidateJobId): Observable<CandidateProfile> {
    const candidateProfileObservable: Observable<CandidateProfile> = this.http
      .get<ResponseModel<CandidateProfile>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_PROFILE + '/' + candidateJobId)
      .pipe(
        map((resCandidateProfile: ResponseModel<CandidateProfile>) => {
          return resCandidateProfile.object;
        })
      );
    return candidateProfileObservable;
  }

  public getCandidateRemarkByCandidateJobId(candidateJobId): Observable<CandidateRemark> {
    const candidateRemarkObservable: Observable<CandidateRemark> = this.http
      .get<ResponseModel<CandidateRemark>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_REMARK + '/' + candidateJobId)
      .pipe(
        map((resCandidateRemark: ResponseModel<CandidateRemark>) => {
          return resCandidateRemark.object;
        })
      );
    return candidateRemarkObservable;
  }

  public getCandidateCurrentInterview(jobId): Observable<CandidateInterview> {
    const initialCandidateInterviewObservable: Observable<CandidateInterview> = this.http
      .get<ResponseModel<CandidateInterview>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_INTERVIEW + `/${jobId}`)
      .pipe(
        map((resCandidateInterview: ResponseModel<CandidateInterview>) => {
          return resCandidateInterview.object;
        })
      );
    return initialCandidateInterviewObservable;
  }

  public getCandidateCurrentRemark(jobId): Observable<CandidateRemark> {
    const initialCandidateRemarkObservable: Observable<CandidateRemark> = this.http
      .get<ResponseModel<CandidateRemark>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_REMARK + `/${jobId}`)
      .pipe(
        map((resCandidateRemark: ResponseModel<CandidateRemark>) => {
          return resCandidateRemark.object;
        })
      );
    return initialCandidateRemarkObservable;
  }

  public updateCandidateInterview(candidateInterview: CandidateInterview): Observable<CandidateInterview> {
    const updateCandidateInterviewObservable: Observable<CandidateInterview> = this.http
      .post<ResponseModel<CandidateInterview>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_INTERVIEW, candidateInterview)
      .pipe(
        map((resCandidateInterview: ResponseModel<CandidateInterview>) => {
          return resCandidateInterview.object;
        })
      );
    return updateCandidateInterviewObservable;
  }

  public updateCandidateAssessment(candidateAssessment: CandidateAssessment): Observable<CandidateAssessment> {
    const updateCandidateAssessmentObservable: Observable<CandidateAssessment> = this.http
      .post<ResponseModel<CandidateAssessment>>(ApiEndpoints.CANDIDATE_JOBS.GET_CANDIDATE_ASSESSMENT, candidateAssessment)
      .pipe(
        map((resCandidateAssessment: ResponseModel<CandidateAssessment>) => {
          return resCandidateAssessment.object;
        })
      );
    return updateCandidateAssessmentObservable;
  }

  public updateCandidateProfile(candidateProfile: CandidateProfile): Observable<CandidateProfile> {
    const candidateProfileObservable: Observable<CandidateProfile> = this.http
      .post<ResponseModel<CandidateProfile>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_PROFILE, candidateProfile)
      .pipe(
        map((resCandidateProfile: ResponseModel<CandidateProfile>) => {
          return resCandidateProfile.object;
        })
      );
    return candidateProfileObservable;
  }

  public updateCandidateRemark(candidateRemark: CandidateRemark): Observable<CandidateRemark> {
    const candidateRemarkObservable: Observable<CandidateRemark> = this.http
      .post<ResponseModel<CandidateRemark>>(ApiEndpoints.CANDIDATE.GET_CANDIDATE_REMARK, candidateRemark)
      .pipe(
        map((resCandidateRemark: ResponseModel<CandidateRemark>) => {
          return resCandidateRemark.object;
        })
      );
    return candidateRemarkObservable;
  }

  submitAssignmentSolution(data: any): Observable<any> {
    return this.http.post(ApiEndpoints.CANDIDATE_JOBS.SUBMIT_ASSIGNMENT_SOLUTION, data);
  }

  public rejectCandidateApplication(candidateJob: CandidateJob): Observable<CandidateJob> {
    const updateCandidateJobObservable: Observable<CandidateJob> = this.http
      .post<ResponseModel<CandidateJob>>(ApiEndpoints.CANDIDATE_JOBS.REJECT_CANDIDATE_APPLICATION, candidateJob)
      .pipe(
        map((candidateJobResponseModal: ResponseModel<CandidateJob>) => {
          return candidateJobResponseModal.object;
        })
      );
    return updateCandidateJobObservable;
  }

}
