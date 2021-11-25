import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '@app/core/services/user.service';
import {User} from '@app/core/model/core.model';
import {APP_ROUTES} from '@app/core/core.constant';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit {

  @Input() userid: number;

  user: User;
  alert = false;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.userService.getUserById(this.userid).subscribe(data => {
      this.user = data;
      console.log(data);
    }, error => console.log(error));
  }

  onSubmit(userForm): void {
    this.userService.updateUser(this.userid, this.user).subscribe(data => {
      // this.goToUserComponent();
      this.router.navigate([APP_ROUTES.DASH_USER_DETAILS], {queryParams: {['success']: true}});
      // userForm.form.reset();
      // this.responseMsg ="Succesfull"
    }, error => console.log(error));

    this.alert = true;
  }

  closeAlert(): void {
    this.alert = false;
  }

}
