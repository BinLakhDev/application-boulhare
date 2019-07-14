import { Moment } from 'moment';

export interface IClientInstantane {
  id?: number;
  nom?: string;
  phone?: string;
  date?: Moment;
  agenceCodeAgence?: string;
  agenceId?: number;
}

export const defaultValue: Readonly<IClientInstantane> = {};
