import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-filter-sidebar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './filter-sidebar.component.html',
  styleUrls: ['./filter-sidebar.component.scss'],
})
export class FilterSidebarComponent {
  @Input() authors: string[] = [];
  @Input() categories: string[] = [];
  @Output() filterChange = new EventEmitter<{ author?: string; category?: string; minRating?: number }>();

  selectedAuthor: string = '';
  selectedCategory: string = '';
  selectedMinRating: number | null = null;

  apply(): void {
    console.log('FilterSidebar applying filters', {
      author: this.selectedAuthor,
      category: this.selectedCategory,
      minRating: this.selectedMinRating,
    });

    this.filterChange.emit({
      author: this.selectedAuthor || undefined,
      category: this.selectedCategory || undefined,
      minRating: this.selectedMinRating !== null ? this.selectedMinRating : undefined
    });
  }


  clear(): void {
    this.selectedAuthor = '';
    this.selectedCategory = '';
    this.selectedMinRating = null;
    this.filterChange.emit({
      author: undefined,
      category: undefined,
      minRating: undefined
    });
  }

}
