import {environment} from '@env/environment';

const API_ENDPOINT = environment.apiUrl;

// API Endpoints
export class ApiEndpoints {

  static readonly API_URL = API_ENDPOINT;

  static readonly PUBLIC_URL = {
    RESUME_SUBMIT: API_ENDPOINT + '/api/candidate/uploadFile',
    JOB_APPLY: API_ENDPOINT + '/public-apis/resume/apply',
    JOBS_LIST: API_ENDPOINT + '/public-apis/jobs/all',
    REFERRER_VERIFYING_USER: API_ENDPOINT + '/public-apis/referrer-verifying-user',
  };

  static readonly AUTH = {
    USER_LOGIN: API_ENDPOINT + '/auth/login',
    USER_LOGOUT: API_ENDPOINT + '/auth/logout',
    FORGOT_PASSWORD: API_ENDPOINT + '/auth/send-forgot-password-email',
    PASSWORD_RESET_SET_NEW_PASS: API_ENDPOINT + '/auth/process-password-update-request',
  };

  static readonly jobs = {
    ADD: API_ENDPOINT + '/jobs',
    ALL: API_ENDPOINT + '/jobs/all',
    UPDATE_JOB : API_ENDPOINT + '/jobs'
  };


  static readonly USERS = {
    ME: API_ENDPOINT + '/users/me',
    ALL: API_ENDPOINT + '/users/all',
    UPDATE_USER: API_ENDPOINT + '/users',
    GET_BY_ID: API_ENDPOINT + '/users',
  };

  static readonly CANDIDATE_JOBS = {
    ALL_CANDIDATE_JOB_LISTING: API_ENDPOINT + '/candidate-jobs',
    ALL_INTERVIEWERS_LISTING: API_ENDPOINT + '/api/interviewer/all',
    CANDIDATE_CURRENT_JOB_BY_USER_ID: API_ENDPOINT + '/candidate-jobs/current',
    GET_CANDIDATE_ASSESSMENT: API_ENDPOINT + '/candidate-jobs/send-assignment',
    SUBMIT_ASSIGNMENT_SOLUTION: API_ENDPOINT + '/candidate-jobs/submit-solution',
    REJECT_CANDIDATE_APPLICATION: API_ENDPOINT + '/candidate-jobs/reject-application',
  };

  static readonly CANDIDATE_FILE = {
    // CANDIDATE_UPLOAD_FILE: API_ENDPOINT + '/candidate-file/upload-file',
    CANDIDATE_ALL_FILES_BY_USER_ID: API_ENDPOINT + '/candidate-file/all/files-by-user-id',
    GET_CANDIDATE_RESUME_BY_CANDIDATE_JOB_ID: API_ENDPOINT + '/candidate-file/resume',
    CANDIDATE_UPLOAD_RESOURCES: API_ENDPOINT + '/candidate-file/upload-resource',
    DOWNLOAD_CANDIDATE_FILE_BY_ID: API_ENDPOINT + '/candidate-file/download-resources',

  };

  static readonly CANDIDATE = {
    GET_CANDIDATE_INTERVIEW: API_ENDPOINT + '/candidate/interview',
    GET_CANDIDATE_PROFILE: API_ENDPOINT + '/candidate/profile',
    GET_CANDIDATE_REMARK: API_ENDPOINT + '/candidate/remark',
  };

  static readonly MESSAGE = {
    SEND_MESSAGE: API_ENDPOINT + '/message/send',
    REPLY_MESSAGE: API_ENDPOINT + '/message/reply',
    GET_ALL_MESSAGES_BY_CANDIDATE_JOB: API_ENDPOINT + '/message/all/by-candidatejob',
  };

  static readonly DASHBOARD_BOX = {
    GET_KPI_VALUES : API_ENDPOINT + '/candidate/getKpiValues',
    GET_RECENT_HIRED: API_ENDPOINT + '/candidate/getRecentHire',
    GET_NEW_CANDIDATES: API_ENDPOINT + '/candidate/getNewCandidates',
    GET_ACTIVE_VACANCIES: API_ENDPOINT + '/job/getActiveVacancies',
    GET_ACTIVE_REFERRALS: API_ENDPOINT + '/candidate/getActiveReferrals'
  };

}
