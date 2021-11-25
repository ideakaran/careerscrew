import {environment} from '@env/environment';
import {UserRole} from '@app/core/model/core.model';

export const APP_PROPERTIES = {
  appBrandName: environment.appBrandName || 'My App',
  maxResumeFileSize: environment.maxResumeFileSize || null,
  allowedFileTypes: environment.allowedFileTypes || null,
  referrerEmailPattern: environment.referrerEmailPattern || null
};

export interface RoutePathAndRoles {
  allowedRoles: UserRole[];
}

// Sync this with routing modules
export enum APP_ROUTES {
  HOME = '/',
  LOGIN = '/login',
  VERIFY = '/verify',
  FAQ = '/faq',

  DASHBOARD = '/dashboard',
  DASH_SETTINGS = '/dashboard/settings',
  DASH_PROFILE = '/dashboard/profile',

  DASH_USER_DETAILS = '/dashboard/user-details',
  DASH_USER_DETAILS_UPDATE = '/dashboard/user-details/update',

  DASH_JOB_DETAILS = '/dashboard/job-posting',
  DASH_JOB_DETAILS_NEW = '/dashboard/job-posting/new',
  DASH_JOB_DETAILS_UPDATE = '/dashboard/job-posting/update',
}

export const APP_ROUTES_AND_ACCESS: { [key in APP_ROUTES]?: RoutePathAndRoles } = {
  [APP_ROUTES.HOME]: {allowedRoles: []},
  [APP_ROUTES.LOGIN]: {allowedRoles: []},
  [APP_ROUTES.VERIFY]: {allowedRoles: []},
  [APP_ROUTES.FAQ]: {allowedRoles: []},

  [APP_ROUTES.DASHBOARD]: {allowedRoles: []},
  [APP_ROUTES.DASH_SETTINGS]: {allowedRoles: []},
  [APP_ROUTES.DASH_PROFILE]: {allowedRoles: []},

  [APP_ROUTES.DASH_USER_DETAILS]: {allowedRoles: [UserRole.ROLE_ADMIN, UserRole.ROLE_MANAGER]},
  [APP_ROUTES.DASH_USER_DETAILS]: {allowedRoles: [UserRole.ROLE_ADMIN, UserRole.ROLE_MANAGER]},

  [APP_ROUTES.DASH_JOB_DETAILS]: {allowedRoles: [UserRole.ROLE_ADMIN, UserRole.ROLE_MANAGER]},
  [APP_ROUTES.DASH_JOB_DETAILS_NEW]: {allowedRoles: [UserRole.ROLE_ADMIN, UserRole.ROLE_MANAGER]},
  [APP_ROUTES.DASH_JOB_DETAILS_UPDATE]: {allowedRoles: [UserRole.ROLE_ADMIN, UserRole.ROLE_MANAGER]},

};

// QueryParamKeys are set by the BackendService (Email flow or via other services)
export class QueryParamKey {

  static readonly ERROR = 'error';
  static readonly EMAIL = 'email';
  static readonly API_ENDPOINT_TO_HIT = 'apiEndpointToHit';

  // referrer verifying query Params
  static readonly VERIFICATION_CODE = 'verificationCode';

  // Password reset query params
  static readonly IS_PROCESS_PASSWORD_RESET = 'isProcessPasswordReset';
  static readonly FORGOT_PASSWORD_VER_CODE = 'forgotPasswordVerCode';


}

// Params keys used in UI for communication bettween pages via QueryParam
export class QueryParamUIKey {

  static readonly ORIGINAL_REQUEST_URI = 'originalRequestUri';
  static readonly REFERRED_FROM_URI = 'referredFromUri';

  static readonly DEFAULT_INFO_MESSAGE = 'defaultInfoMessage';
  static readonly REGISTRATION_SUCCESSFUL = 'registrationSuccess';
  static readonly EMAIL_VERIFICATION_SUCCESSFUL = 'emailVerificationSuccess';
  static readonly PASSWORD_RESET_SUCCESSFUL = 'passwordResetSuccess';
  static readonly LOGOUT_SUCCESSFUL = 'logoutSuccess';


}


export const MESSAGE_RESPONSE_CONSTANTS = {

  Success_Action: 'Action Success',
  Operation_Failed_MSG: 'Oops! Something went wrong !!',

};

export const ERROR_CODES_CONSTANTS = {
  ServerDown: 0,
  BadRequest: 400,
  Unauthorized: 401,
  ResourceNotFound: 404,
  InternalServerError: 500,
};
