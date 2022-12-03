import { Pipe, PipeTransform } from '@angular/core';
import { CredentialService } from '../services/credential.service';

@Pipe({
  name: 'password'
})
export class PasswordPipe implements PipeTransform {
  clearPassword!: string;
  constructor(private credentialService: CredentialService){}

  transform(password: string, visible: boolean): string {   
    this.credentialService.decryptCredentialPassword(password).subscribe({
      next: (data) => {
        this.clearPassword = data
      }
    });

    if(visible) return this.clearPassword;
    else return '********';
  }

}
