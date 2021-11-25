export enum UserRole {
  ROLE_USER = 'ROLE_USER', ROLE_ADMIN = 'ROLE_ADMIN', ROLE_MANAGER = 'ROLE_MANAGER'
}

export enum ApplicationStatus {
  PENDING = 'PENDING',
  SUBMITTED = 'SUBMITTED',
  NOT_ACCEPTED = 'NOT_ACCEPTED',
  INTERVIEW_SCHEDULED = 'INTERVIEW_SCHEDULED',
  SELECTED = 'SELECTED',
  UNSELECTED = 'UNSELECTED'
}

export enum Experience {
  JUNIOR = 'JUNIOR',
  MID_LEVEL = 'MID_LEVEL',
  SENIOR = 'SENIOR',
  PRINCIPAL = 'PRINCIPAL',
  TECH_LEAD = 'TECH_LEAD'
}

export interface ResponseModel<T> {
  message: string;
  object: T;
}

export interface PageRequest {
  page: number;
  size: number;
  sort: string;
  direction: string;
}

export interface PageableResponse<T> {
  response: T;
  totalElements: number;
  size?: number;
  numberOfElements?: number;
}

export interface JobApplyRequest {
  fullName: string;
  email: string;
  contactNumber: string;
  jobId: string;
  appliedDate?: string;
  refererFullName: string;
  refererEmail: string;
}

export interface User {
  id: number;
  fullName: string;
  email: string;
  roles: Array<UserRole>;
  contactNumber: string;
}

export interface Job {
  id: number;
  title: string;
  position?: string;
  description?: string;
  isActive?: boolean;
}

export interface Interviewer {
  id: number;
  name: string;
  email: string;
}

export interface CandidateJob {
  id?: number;
  userEntity: User;
  appliedDate: string;
  job: Job;
  applicationStatus: ApplicationStatus;
  refererFullName: string;
  refererEmail: string;
  isVerifiedByReferer: boolean;
  msgForCandidate: string;
  assignment: string;
  githubLinks: string[];
}

export interface CandidateFile {
  id: number;
  fileName: string;
  isPrimaryResume: true;
  path: string;
  uploadDate: string;
  candidateJobId: number;
}

export interface CandidateJobInitial {
  description: string;
}

export interface CandidateInterview {
  id?: number;
  stage: number;
  interviewDate: string;
  description: string;
  candidateJobId: number;
  interviewersId: Array<number>;
}

export interface CandidateAssessment {
  assignment: string;
  id: number;
}

export interface KpiValues {
  recentHires: number;
  newCandidate: number;
  activeVacancies: number;
  activeRefferals: number;
}

export interface CandidateProfile {
  id: number;
  education: number;
  candidateJobId: number;
  training: string;
  experience: string;
}

export interface CandidateRemark {
  id?: string;
  remarks: string;
  tackling: string;
  communication: string;
  stability: string;
  grace: string;
  writtenTestScore: number;
  isSuitableForHiring: boolean;
  personality: string;
  knowledge: string;
  candidateJobId: number;
}

export interface MessageSendReplyRequest {
  id?: number;
  candidateJobId: number;
  subject?: string;
  initialMsg?: string;
  sendingUserId?: number;
  replyMsg?: string;
  replyingUserId?: number;
  isHidden?: boolean;
}


export interface MessageSendReplyResponse {
  id: number;
  candidateJob: CandidateJob;
  subject: string;
  initialMsg?: string;
  sendingUserEntity?: User;
  replyMsg?: string;
  replyingUserEntity?: User;
  isHidden?: boolean;
}
