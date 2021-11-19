export class UserFetch {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    mobile: number;
    education: string;
    university: string;
    referer: string;
    refererEmail: string

    constructor(id: string, firstName: string, lastName: string, email: string, mobile: number, education: string, university: string, referer: string, refererEmail: string) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.education = education;
        this.university = university;
        this.referer = referer;
        this.refererEmail = refererEmail;
    }
}