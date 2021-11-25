import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-faq-page',
  templateUrl: './faq-page.component.html',
  styleUrls: ['./faq-page.component.scss']
})
export class FaqPageComponent implements OnInit {

  activeIds: string[] = ['a1', 'a2', 'a3', 'a4'];

  constructor() { }

  ngOnInit(): void {
  }

}
