import {Component, Input, OnInit} from '@angular/core';
import {UserService} from '@app/core/services/user.service';
import {ActivatedRoute} from '@angular/router';
import {User} from '@app/core/model/core.model';


@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss']
})
export class UserInfoComponent implements OnInit {

  id: number;
  user: User;

  @Input() userId: string;
  showResume: boolean;

  constructor(
    private service: UserService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.id = Number(this.userId ? this.userId : this.route.snapshot.params.id);
    this.service.getUserById(this.id).subscribe(data => {
      this.user = data;
      console.log(data);
    }, error => console.log(error));

    // this.showResume = true;
  }

  // onClick(): void {
  //   this.showResume = !this.showResume;
  // }
}
