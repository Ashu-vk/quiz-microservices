import { AfterViewInit, Directive, ElementRef } from '@angular/core';

@Directive({ selector: '[appAutofocus]', standalone: true })
export class AutofocusDirective implements AfterViewInit {
  constructor(private el: ElementRef<HTMLInputElement>) {}

  ngAfterViewInit(): void {
    // small timeout to ensure element is ready
    setTimeout(() => this.el.nativeElement.focus(), 0);
  }
}
