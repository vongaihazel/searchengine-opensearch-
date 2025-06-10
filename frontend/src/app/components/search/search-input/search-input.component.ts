import { Component, Output, EventEmitter, AfterViewInit, ViewChild, ElementRef} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-search-input',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.scss'],
})
export class SearchInputComponent implements AfterViewInit {
  query = '';
  @Output() onSearch = new EventEmitter<string>();
  @ViewChild('searchBox') searchBox!: ElementRef;

  emitSearch() {
    this.onSearch.emit(this.query);
  }

  ngAfterViewInit() {
      this.searchBox.nativeElement.focus();
    }
}
