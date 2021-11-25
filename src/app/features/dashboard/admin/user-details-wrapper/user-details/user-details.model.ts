import {Column, ColumnType} from '@app/shared/simple-table/simple-table.model';
import {ApplicationStatus} from '@app/core/model/core.model';

// Controls css class as per data value
const applicationStatusCSSValueMap = {
  [ApplicationStatus.PENDING]: 'bg-dark',
  [ApplicationStatus.SUBMITTED]: 'bg-secondary',
  [ApplicationStatus.NOT_ACCEPTED]: 'bg-danger',
  [ApplicationStatus.INTERVIEW_SCHEDULED]: 'bg-primary',
  [ApplicationStatus.UNSELECTED]: 'bg-danger',
  [ApplicationStatus.SELECTED]: 'bg-success',
};

export const CandidateJobColumn: Column[] = [
  {id: 1, name: 'id', label: 'SN', type: ColumnType.STRING},
  {id: 2, name: 'userEntity', label: 'Full Name', type: ColumnType.OBJECT, bindKeys: ['userEntity', 'fullName']},
  {id: 3, name: 'userEntity', label: 'Email', type: ColumnType.OBJECT, bindKeys: ['userEntity', 'email']},
  {id: 4, name: 'job', label: 'Applied Position', type: ColumnType.OBJECT, bindKeys: ['job', 'title']},
  {
    id: 5,
    name: 'applicationStatus',
    label: 'Application Status',
    cssClasses: 'badge rounded-pill text-uppercase',
    cssValueMap: applicationStatusCSSValueMap,
    type: ColumnType.STRING
  },
];

export const CandidateInterviewColumn: Column[] = [
  {id: 1, name: 'id', label: 'SN', type: ColumnType.NUMBER},
  {id: 2, name: 'interviewDate', label: 'Interview Date', type: ColumnType.DATE},
  {id: 3, name: 'time', label: 'Interview Time', type: ColumnType.STRING},
  {id: 4, name: 'interviewers', label: 'Interviewers', type: ColumnType.STRING},
  {id: 4, name: 'description', label: 'Description', type: ColumnType.STRING},
];

