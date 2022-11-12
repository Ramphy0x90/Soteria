import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userStatus: EventEmitter<boolean> = new EventEmitter;
  
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

  saveLogInUser(user: User, token: string): void {
    window.sessionStorage.clear();

    this.userStatus.emit(true);
    user.password = '';
    window.sessionStorage.setItem('user', JSON.stringify(user));
    window.sessionStorage.setItem('token', token);
  }

  getLoggedUser(): User | null {
    let user = window.sessionStorage.getItem('user');
    if (user) return JSON.parse(user);

    return null;
  }

  getLoggedUserToken(): string {
    let token = window.sessionStorage.getItem('token');
    return token ? token : '';
  }

  isLoggedIn(): boolean {
    return window.sessionStorage.getItem('user') !== null;
  }

  logOut(): void {
    this.userStatus.emit(false);
    window.sessionStorage.clear();
  }
}
