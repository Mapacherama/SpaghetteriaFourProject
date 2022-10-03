import {TestBed} from '@angular/core/testing';

import {ApiService} from './api.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {Product} from '../_models/Product';
import {environment} from '../../environments/environment';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

describe('ApiServiceService', () => {
  let service: ApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        [HttpClientTestingModule],
        [RouterTestingModule],
        [FormsModule],
        [ReactiveFormsModule]
      ],
    });
    service = TestBed.inject(ApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // Sam
  it('calling admin function without permission shoulndt run', async () => {
    sessionStorage.setItem('role','USER')
    expect(await service.deleteContact(12) ).toBe(undefined);
  });


});
