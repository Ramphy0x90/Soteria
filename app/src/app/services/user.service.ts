import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private httpClient: HttpClient) { }

  logIn(user: User): Observable<any> {
    return this.httpClient.post(
      `${environment.server}/user/log-in`, user, this.httpOptions
    );
  }

  signIn(user: User): Observable<any> {
    return this.httpClient.post(
      `${environment.server}/user/sign-in`, user, this.httpOptions
    );
  }
}
