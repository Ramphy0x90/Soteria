import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RouteTransitionAnimations } from './route-transition-animations';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass'],
  animations: [RouteTransitionAnimations]
})
export class AppComponent {
  title = 'app';

  prepareRoute(outlet: RouterOutlet) {
    return outlet && 
      outlet.activatedRouteData && 
      outlet.activatedRouteData['animationState'];
   }
}
