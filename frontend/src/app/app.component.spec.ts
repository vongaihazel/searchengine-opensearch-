import { TestBed } from '@angular/core/testing'; // Import TestBed for testing
import { AppComponent } from './app.component'; // Import the component to be tested

describe('AppComponent', () => {
  // Setup before each test
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent], // Import the AppComponent
    }).compileComponents(); // Compile the component templates
  });

  // Test to check if the app component is created
  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent); // Create component fixture
    const app = fixture.componentInstance; // Get the component instance
    expect(app).toBeTruthy(); // Expect the component to be truthy (i.e., created)
  });

  // Test to verify the title property
  it(`should have the 'searchengine' title`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('searchengine'); // Expect the title to equal 'searchengine'
  });

  // Test to check if the title is rendered correctly
  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges(); // Trigger change detection
    const compiled = fixture.nativeElement as HTMLElement; // Get the native element
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, searchengine'); // Check if the h1 contains the expected text
  });
});
