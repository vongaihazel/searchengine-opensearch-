import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss'],
})
export class SearchResultsComponent {
  @Input() results: any[] = [];
  @Input() searchTerm: string = '';

  constructor(private sanitizer: DomSanitizer) {}

    highlightTerm(text: string): SafeHtml {
      if (!this.searchTerm) return text;
      const escapedTerm = this.escapeRegExp(this.searchTerm);
      const regex = new RegExp(`(${escapedTerm})`, 'gi');
      const highlighted = text.replace(regex, `<mark>$1</mark>`);
      return this.sanitizer.bypassSecurityTrustHtml(highlighted);
    }

    private escapeRegExp(str: string): string {
      return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    }

}
