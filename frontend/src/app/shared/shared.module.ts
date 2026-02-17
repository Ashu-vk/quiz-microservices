import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TruncatePipe } from './pipes/truncate.pipe';
import { AutofocusDirective } from './autofocus.directive';

@NgModule({
  imports: [CommonModule, TruncatePipe, AutofocusDirective],
  declarations: [],
  exports: [TruncatePipe, AutofocusDirective],
})
export class SharedModule {}
