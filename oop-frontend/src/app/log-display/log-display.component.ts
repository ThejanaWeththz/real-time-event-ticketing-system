import { Component, ElementRef, ViewChild } from '@angular/core';
import { AppComponent } from '../app.component';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-log-display',
  standalone: true,
  imports: [NgFor],
  templateUrl: './log-display.component.html',
})
export class LogDisplayComponent {
  @ViewChild('display')
  latestLog!: ElementRef;
  logs: String[];
  noLogs: boolean;

  constructor(private host: AppComponent) {
    this.logs = [];
    this.noLogs = false;
  }

  async ngOnInit(): Promise<void> {
    this.loadLog(true);
  }

  ngAfterViewChecked() {
    this.autoScroll();
  }

  async loadLog(initial: boolean): Promise<void> {
    if (initial) this.noLogs = true;
    this.host.client
      .get('/logs', {
        responseType: 'stream',
        adapter: 'fetch',
      })
      .then(async (response) => {
        const stream = response.data;
        this.noLogs = false;

        const reader = stream.pipeThrough(new TextDecoderStream()).getReader();
        while (true) {
          const { value, done } = await reader.read();
          if (done) {
            console.log('done');
            break;
          }
          if (value != '' && value != 'data:') {
            this.logs.push(value.replace('data:', ''));
            console.log(value);
          }
        }
      });
  }

  autoScroll(): void {
    this.latestLog.nativeElement.scrollTop =
      this.latestLog.nativeElement.scrollHeight;
  }
}
