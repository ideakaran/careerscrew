import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Users } from '../entities/Users';
import { map, Observable } from 'rxjs';
import { UserFetch } from '../entities/UserFetch';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  headers = new HttpHeaders().set('Content-Type', 'application-json').set('Accept', 'application/json');
  httpOptions = {
    headers: this.headers
  }

  url: string = "http://localhost:3000/users";

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get<Users[]>(this.url);
  }

  deleteUser(id: string): Observable<Users> {
    const url = `${this.url}/${id}`;
    return this.http.delete<Users>(url, this.httpOptions);
  }

  getUpdateUser(id: string): Observable<Users> {
    const url = `${this.url}/${id}`;
    return this.http.get<Users>(url, this.httpOptions);
  }

  updateUser(user: Users): Observable<Users> {
    const url = `${this.url}/${user.id}`;
    return this.http.post<Users>(url, user, this.httpOptions).pipe(
      map(() => user)
    );
  }
}
