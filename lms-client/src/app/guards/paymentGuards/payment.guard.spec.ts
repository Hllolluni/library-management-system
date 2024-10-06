import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { paymentGuard } from './payment.guard';

describe('paymentGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => paymentGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
