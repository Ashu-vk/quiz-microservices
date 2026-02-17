import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './header/header';
import { SharedModule } from './shared/shared.module';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, SharedModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal("Quiz App");
}
