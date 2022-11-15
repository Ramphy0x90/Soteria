import { Component, OnInit } from '@angular/core';
import { Credential } from 'src/app/models/credential';
import { CredentialService } from 'src/app/services/credential.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home-app',
  templateUrl: './home-app.component.html',
  styleUrls: ['./home-app.component.sass']
})
export class HomeAppComponent implements OnInit {
  loggedUser!: String | null | undefined;
  credentials!: Credential[];

  selectedCredential!: Credential;

  constructor(private userService: UserService, private credentialService: CredentialService) { }

  ngOnInit(): void {
    this.fetchCredentials();
    this.loggedUser = this.userService.getLoggedUser()?.userName;
  }

  fetchCredentials(): void {
    this.credentialService.getCredentials().subscribe({
      next: (data) => {
        this.credentials = data;
        this.selectedCredential = this.credentials[0]
      }
    });
  }

  selectCredential(credential: Credential): void {
    this.selectedCredential = credential;
  }

  deleteCredential(): void {
    if(this.selectedCredential && this.selectedCredential.id) {
      this.credentialService.deleteCredential(this.selectedCredential.id).subscribe({
        next: () => {
          this.fetchCredentials();
        }
      });
    }
  }

}
