export interface PagesModel {
  /** Text, which is displayed in the link */
  text: string;
  /** Page number */
  number: number;
  /** If `true`, then this is the current page */
  active: boolean;
}

export interface PageChangedEvent {
  itemsPerPage: number;
  page: number;
}
