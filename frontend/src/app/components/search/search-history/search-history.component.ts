import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-search-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './search-history.component.html',
  styleUrls: ['./search-history.component.scss'],
})
export class SearchHistoryComponent {
  @Input() history: string[] = [];
  @Output() onSelect = new EventEmitter<string>();

  selectItem(item: string): void {
      this.onSelect.emit(item);
    }
}
