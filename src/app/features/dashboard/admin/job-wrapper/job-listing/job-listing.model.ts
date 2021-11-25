import {Column, ColumnType} from '@app/shared/simple-table/simple-table.model';

export const JobColumn: Column[] = [
  {id: 1, name: 'id', label: 'SN', type: ColumnType.STRING},
  {id: 2, name: 'title', label: 'Title', type: ColumnType.STRING},
  {id: 3, name: 'position', label: 'Position', type: ColumnType.STRING},
  {
    id: 4,
    name: 'isActive',
    label: 'Is Active',
    cssClasses: 'badge rounded-pill text-uppercase',
    cssValueMap: {true: 'bg-primary', false: 'bg-dark'},
    type: ColumnType.STRING
  },
];
