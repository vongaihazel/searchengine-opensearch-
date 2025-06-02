import { ComponentFixture, TestBed } from '@angular/core/testing'; // Import necessary testing utilities
import { SearchComponent } from './search.component'; // Import the component to be tested

describe('SearchComponent', () => {
  let component: SearchComponent; // Declare the component variable
  let fixture: ComponentFixture<SearchComponent>; // Declare the fixture variable

  beforeEach(async () => {
    // Configure the testing module
    await TestBed.configureTestingModule({
      imports: [SearchComponent] // Import the component
    })
      .compileComponents(); // Compile the component templates

    // Create the component fixture
    fixture = TestBed.createComponent(SearchComponent);
    component = fixture.componentInstance; // Get the component instance
    fixture.detectChanges(); // Trigger change detection
  });

  it('should create', () => {
    expect(component).toBeTruthy(); // Check if the component is created successfully
  });
});
