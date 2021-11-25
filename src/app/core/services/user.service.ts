import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ApiEndpoints} from '../app-url.constant';
import {CandidateJob, User} from '../model/core.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  readonly API_ENDPOINT = ApiEndpoints.API_URL;

  constructor(private http: HttpClient) {
  }

  public getAllUsers(): Observable<User[]> {
    const usersObservable: Observable<User[]> = this.http.get<User[]>(ApiEndpoints.USERS.ALL);
    return usersObservable;
  }

  getUserById(id: number): Observable<User> {
    const userObservable: Observable<User> = this.http
      .get<User>(ApiEndpoints.USERS.GET_BY_ID + '/' + id);
    return userObservable;
  }

  updateUser(id: number, user: User): Observable<User> {
    const userEntity = {...user, id};
    const allCandidateJobsObservable: Observable<User> = this.http
      .put<User>(ApiEndpoints.USERS.UPDATE_USER, userEntity);
    return allCandidateJobsObservable;
  }

}
