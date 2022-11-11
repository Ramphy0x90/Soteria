import { trigger, transition, query, style, animate, group } from '@angular/animations';

export const RouteTransitionAnimations = trigger('triggerName', [
  transition('* <=> *', [
    /**
     * What happen to the elements that will be in the transition
     */
    query(':enter, :leave',
      style({ position: 'absolute', width: '100%', height: '100%', zIndex: 2 }),
      { optional: true }
    ),
    group([
      // Element that will be displayed
      query(':enter', [
        style({ opacity: '0' }),
        animate('0.6s linear', style({ opacity: '1' }))
      ], { optional: true }),
      // Old element that will be removed fro screen
      query(':leave', [
        style({ opacity: '1' }),
        animate('0.1s linear', style({ opacity: '0' }))
      ], { optional: true })
    ])
  ])
]);
