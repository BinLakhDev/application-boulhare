export interface IUtilisateur {
  id?: number;
  fullname?: string;
  username?: string;
  password?: string;
  logoContentType?: string;
  logo?: any;
  iban?: string;
  numero?: string;
  email?: string;
  agenceId?: number;
  userEmail?: string;
  userId?: number;
}

export const defaultValue: Readonly<IUtilisateur> = {};
