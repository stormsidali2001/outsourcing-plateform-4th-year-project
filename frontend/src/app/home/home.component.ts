import { Component } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  array = [];
  akram:any
  constructor() {
    axios
      .get('http://localhost:4000/api/pokemons')
      .then((response) => {
        // this.arry.push(...response.data)
        this.akram=response.data.data
        console.log(response.data.data)        
      })
      .catch((error) => {
        console.log(error);
      });
  }
  ngOnInit(): void {}
}
