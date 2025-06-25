import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms'; // import FormsModule for ngModel

@Component({
  selector: 'app-filter-sidebar',
  standalone: true,                // <--- Make it standalone
  imports: [FormsModule],          // <--- Import FormsModule here
  templateUrl: './filter-sidebar.component.html',
  styleUrls: ['./filter-sidebar.component.scss']
})
export class FilterSidebarComponent {
  @Input() categories: string[] = [];
  @Input() authors: string[] = [];
  @Input() ratings: number[] = [];

  category: string = '';
  author: string = '';
  tag: string = '';
  minViews?: number;
  minRating?: number;

  @Output() filtersApplied = new EventEmitter<any>();

  applyFilters(): void {
    const filters = {
      category: this.category || null,
      author: this.author || null,
      tag: this.tag || null,
      minViews: this.minViews ?? null,
      minRating: this.minRating ?? null,
    };
    this.filtersApplied.emit(filters);
  }
}
