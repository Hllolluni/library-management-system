import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookAdminOperationsComponent } from './book-admin-operations.component';

describe('BookAdminOperationsComponent', () => {
  let component: BookAdminOperationsComponent;
  let fixture: ComponentFixture<BookAdminOperationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookAdminOperationsComponent]
    });
    fixture = TestBed.createComponent(BookAdminOperationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
