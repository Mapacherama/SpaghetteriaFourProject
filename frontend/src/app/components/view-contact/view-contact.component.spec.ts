import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewContactComponent } from './view-contact.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

describe('ViewContactComponent', () => {
  let component: ViewContactComponent;
  let fixture: ComponentFixture<ViewContactComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        [HttpClientTestingModule],
        [RouterTestingModule],
      ],
      declarations: [ ViewContactComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render title with text contactpersonen', () => {
    // tslint:disable-next-line:no-shadowed-variable
    const fixture = TestBed.createComponent(ViewContactComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector("#contactHeader").textContent).toContain('Contactpersonen');
  });
});
