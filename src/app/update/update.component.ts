import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserFetch } from '../entities/UserFetch';
import { Users } from '../entities/Users';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {

  constructor(public route: ActivatedRoute, public router: Router, public userService: UserService) {

   }

   val:any;
   users: Users[] = [];
   user: UserFetch

  ngOnInit(): void {
    let sub = this.route.params.subscribe(params => {
      this.val = params['id'];
    })
    console.log("id: ",this.val);
    this.userService.getUpdateUser(this.val).subscribe(data => {
      this.user = data;
    })
  }

  update() {
    this.userService.updateUser(this.user).subscribe(data => {
      this.getUsers();
      this.router.navigate(['']);
    });
  }

  getUsers() {
    this.userService.getUsers().subscribe((response) => {
      this.users=response;
    })
  }

}
