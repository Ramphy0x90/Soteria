import { Component, Input, OnInit } from '@angular/core';
import { Credential } from 'src/app/models/credential';
import { Entity } from 'src/app/models/entity';
import { CredentialService } from 'src/app/services/credential.service';
import { EntityService } from 'src/app/services/entity.service';

@Component({
  selector: 'app-credential-form',
  templateUrl: './credential-form.component.html',
  styleUrls: ['./credential-form.component.sass']
})
export class CredentialFormComponent implements OnInit {
  @Input() credential!: Credential;
  // 0 -> Read | 1 -> New | 2 -> Edit
  @Input() mode: number = 0;

  passwordVisible: boolean = false;
  credentialForm: Credential = {
    id: null,
    entity: null,
    entityId: 0,
    userName: '',
    password: ''
  };
  invalidCredentials: boolean = false;
  errorMessage = '';
  formSubmitted: boolean = false;
  entities!: Entity[];


  constructor(private entityService: EntityService, private credentialService: CredentialService) { }

  ngOnInit(): void {
    this.entityService.getEntities().subscribe({
      next: (data) => {
        this.entities = data;
      }
    });

    if(this.mode == 2) {
      this.credentialForm = {
        id: this.credential.id,
        entity: this.credential.entity,
        entityId: this.credential.entity?.id,
        userName: this.credential.userName,
        password: this.credential.password
      }
    }
  }

  passwordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }

  onSubmit(): void {
    let credential: Credential = this.credentialForm;

    this.credentialService.addCredential(credential).subscribe({
      next: (data) => {
        console.log(data)
      },
      error: (err) => {
        this.invalidCredentials = true;
      }
    });
  }
}
