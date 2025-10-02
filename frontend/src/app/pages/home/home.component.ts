import { CommonModule } from '@angular/common';
import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Recipe } from '../../model/recipe';
import { RecipeService } from '../../service/recipe.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  @ViewChild('dropdownRef') dropdownRef!: ElementRef;
  recipes: Recipe[] = [];
  displayedRecipes: Recipe[] = [];

  tags: string[] = [];
  selectedTag = '';
  searchQuery = '';
  showTagDropdown = false;

  loading = false;
  error = '';

  sortTimeAsc: boolean | null = null;

  constructor(private recipeService: RecipeService, private eRef: ElementRef) {}

  ngOnInit() {
    this.loadUsers();
  }

  @HostListener('document:click', ['$event.target'])
  onClickOutside(targetElement: HTMLElement) {
    const clickedInside =
      this.dropdownRef?.nativeElement.contains(targetElement);
    if (!clickedInside && this.showTagDropdown) {
      this.showTagDropdown = false;
    }
  }

  loadUsers() {
    this.loading = true;
    this.error = '';
    this.recipeService.getAllRecipes().subscribe({
      next: (recipes) => {
        this.recipes = recipes;
        this.displayedRecipes = [...recipes];
        this.tags = Array.from(new Set(recipes.map((r) => r.tags).flat()));
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load recipes.';
        this.loading = false;
      },
    });
  }

  searchRecipes() {
    if (!this.searchQuery.trim()) {
      this.displayedRecipes = [...this.recipes];
      this.applyFiltersAndSort();
      return;
    }

    this.loading = true;
    this.error = '';
    this.recipeService.searchRecipesByQuery(this.searchQuery).subscribe({
      next: (recipes) => {
        this.recipes = recipes;
        this.displayedRecipes = [...recipes];
        this.tags = Array.from(new Set(recipes.map((r) => r.tags).flat()));
        this.selectedTag = '';
        this.sortTimeAsc = null;
        this.loading = false;
      },
      error: () => {
        this.error = 'Search failed.';
        this.loading = false;
      },
    });
  }

  filterByTag() {
    this.applyFiltersAndSort();
  }

  sortByCookTime() {
    if (this.sortTimeAsc === null) {
      this.sortTimeAsc = true;
    } else {
      this.sortTimeAsc = !this.sortTimeAsc;
    }
    this.applyFiltersAndSort();
  }

  resetFilters() {
    this.searchQuery = '';
    this.selectedTag = '';
    this.sortTimeAsc = null;
    this.loadUsers();
  }

  private applyFiltersAndSort() {
    let filtered = [...this.recipes];

    if (this.selectedTag) {
      filtered = filtered.filter((r) => r.tags.includes(this.selectedTag));
    }

    if (this.sortTimeAsc !== null) {
      filtered.sort((a, b) =>
        this.sortTimeAsc
          ? a.cookTimeMinutes - b.cookTimeMinutes
          : b.cookTimeMinutes - a.cookTimeMinutes
      );
    }

    this.displayedRecipes = filtered;
  }
}
