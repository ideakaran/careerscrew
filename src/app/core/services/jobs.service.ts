import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Job, ResponseModel} from '@app/core/model/core.model';
import {ApiEndpoints} from '@app/core/app-url.constant';
import {Observable, pipe} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class JobsService {

  constructor(private http: HttpClient) {
  }

  addJobs(job: Job): Observable<Job> {
    return this.http.post<Job>(ApiEndpoints.jobs.ADD, job);
  }

  getAllJobs(): Observable<Job[]> {
    const jobObservable: Observable<Job[]> = this.http.get<ResponseModel<Job[]>>(ApiEndpoints.jobs.ALL).pipe(
      map(
        (jobs: ResponseModel<Job[]>) => {
          return jobs.object;
        })
    );
    return jobObservable;
  }

  getJobById(jobId: number): Observable<Job> {
    const jobObservable: Observable<Job> = this.http.get<ResponseModel<Job>>(ApiEndpoints.jobs.ADD + '/' + jobId).pipe(
      map(
        (job: ResponseModel<Job>) => {
          return job.object;
        })
    );
    return jobObservable;
  }

  updateJob(id: number, job: Job): Observable<Job> {
    const jobEntity = {...job, id};
    const updatedtJobObservable = this.http.put<Job>(ApiEndpoints.jobs.UPDATE_JOB, jobEntity);
    return updatedtJobObservable;
  }
}
