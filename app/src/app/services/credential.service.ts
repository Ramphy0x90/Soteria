import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Credential } from 'src/app/models/credential';

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

  addCredential(credential: Credential): Observable<any> {
    return this.httpClient.post(
      `${environment.server}/credential/add`, credential, this.httpOptions
    );
  }
}
