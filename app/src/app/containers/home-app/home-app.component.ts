import { Component, OnInit } from '@angular/core';
import { Credential } from 'src/app/models/credential';
import { CredentialForm } from 'src/app/models/credential-form';
import { CredentialService } from 'src/app/services/credential.service';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home-app',
  templateUrl: './home-app.component.html',
  styleUrls: ['./home-app.component.sass']
})
export class HomeAppComponent implements OnInit {
  loggedUser!: String | null | undefined;
  credential!: Credential;
  credentials: Credential[] = [];

  credentialFormMode = 1;
  credentialFormTitle = ["Add new credential", "Edit credential"];
  credentialForm!: CredentialForm;

  selectedCredential!: Credential;

  constructor(private userService: UserService,
    private credentialService: CredentialService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.fetchCredentials();
    this.initCredential();
    this.loggedUser = this.userService.getLoggedUser()?.userName;
  }

  initCredential() {
    this.credential =  {
      id: null,
      entity: null,
      entityId: 0,
      userName: '',
      password: ''
    };

    console.log(this.credentialForm)

    if(this.credentialForm !== undefined) {
      console.log("ada")
      this.credentialForm.form.resetForm();
    }
  }

  fetchCredentials(): void {
    this.credentialService.getCredentials().subscribe({
      next: (data: Credential[]) => {
        this.credentials = data;
        this.selectedCredential = this.credentials[0];
      }
    });
  }

  setCredentialFormMode(mode: number): void {
    this.credentialFormMode = mode;

    // On update form
    if(mode == 2) {
      this.credential = this.selectedCredential;
    }
  }

  selectCredential(credential: Credential): void {
    this.selectedCredential = credential;
  }

  saveCredential(credentialForm: CredentialForm): void {
    this.credentialForm = credentialForm;
    let credential: Credential = credentialForm.data;
    let form = credentialForm.form;

    if(this.credentialFormMode == 1) {
      if(form.valid) {
        this.credentialService.addCredential(credential).subscribe({
          next: () => {
            this.initCredential();
            this.fetchCredentials();
            this.toastr.success('Credential created');
          },
          error: (err: any) => {
            this.toastr.error(err.error);
          }
        });
      } else {
        this.toastr.error('Invalid data');
      }
    } else if(this.credentialFormMode == 2) {
      this.credentialService.updateCredential(credential).subscribe({
        next: () => {
          this.fetchCredentials();
          this.toastr.success('Credential updated');
        },
        error: (err: any) => {
          this.toastr.error(err.error);
        }
      });
    }
  }

  deleteCredential(): void {
    if(this.selectedCredential && this.selectedCredential.id) {
      this.credentialService.deleteCredential(this.selectedCredential.id).subscribe({
        next: () => {
          this.fetchCredentials();
          this.toastr.success('Credential deleted');
        }
      });
    }
  }

}
