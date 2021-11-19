import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Users } from '../entities/Users';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-job-dashboard',
  templateUrl: './job-dashboard.component.html',
  styleUrls: ['./job-dashboard.component.css']
})
export class JobDashboardComponent implements OnInit {
  users: Users[] = [];
  firstName: string;
  p: number = 1;
  constructor(public userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe((response) => {
      this.users=response;
    })
  }
  
  Search() {
    if (this.firstName == "") {
      this.ngOnInit();
    } else {
      this.users = this.users.filter(res => {
        return res.firstName.toLocaleLowerCase().match(this.firstName.toLocaleLowerCase());
      });
      console.log(this.users.length);
      
    }
  }


  key:string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }


  deleteRow(val: string) {
    if(confirm("Are you sure you want to delete? ")) {
      this.userService.deleteUser(val).subscribe(data => {
        //todo: check if delete was successful
        this.userService.getUsers().subscribe((response) => {
          this.users=response;
        })
      });
    }
    
  }

  update(id: string) {
    this.router.navigate(['/update', id]);
  }

}
