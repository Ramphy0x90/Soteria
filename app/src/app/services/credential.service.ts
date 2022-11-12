import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class CredentialService {
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private httpClient: HttpClient) { }

  getCredentials(): Observable<any> {
    return this.httpClient.get(
      `${environment.server}/credential/all`, this.httpOptions
    );
  }

  getCredential(id: number): Observable<any> {
    return this.httpClient.get(
      `${environment.server}/credential/${id}`, this.httpOptions
    );
  }
}
