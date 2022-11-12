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
  credentials: Credential[] = [
    {id: 1, entity: 1, userName: 'Ramphy0x90', password: 'Ramphy123@'},
    {id: 2, entity: 2, userName: '_ramphy_', password: 'password'},
    {id: 3, entity: 3, userName: 'ramphy_an@outlook.com', password: 'helloWorld'}
  ];

  selectedCredential: Credential = this.credentials[0];

  constructor(private userService: UserService, private credentialService: CredentialService) { }

  ngOnInit(): void {
    this.credentialService.getCredentials().subscribe({
      next: (data) => {
        this.credentials = data;
      }
    })
    this.loggedUser = this.userService.getLoggedUser()?.userName
  }

  selectCredential(credential: Credential): void {
    this.selectedCredential = credential;
  }

  deleteCredential(): void {

  }

}
