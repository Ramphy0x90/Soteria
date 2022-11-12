import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'password'
})
export class PasswordPipe implements PipeTransform {

  transform(password: string, visible: boolean): string {
    if(visible) return password;
    else return '********';
  }

}
