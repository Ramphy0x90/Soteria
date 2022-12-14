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

  updateCredential(credential: Credential): Observable<any> {
    return this.httpClient.put(
      `${environment.server}/credential/update/${credential.id}`, credential, this.httpOptions
    );
  }

  deleteCredential(credentialId: number): Observable<any> {
    return this.httpClient.delete(
      `${environment.server}/credential/delete/${credentialId}`
    );
  }

  decryptCredentialPassword(credentialPassword: string): Observable<any> {
    return this.httpClient.post(
      `${environment.server}/credential/decrypt/`, credentialPassword, {responseType: 'text'}
    );
  }
}
