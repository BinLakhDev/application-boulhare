export interface IAgence {
  id?: number;
  codeAgence?: string;
  longitude?: string;
  latitude?: string;
  adresseAgence?: string;
  telSiege?: string;
  banqueNameBanque?: string;
  banqueId?: number;
}

export const defaultValue: Readonly<IAgence> = {};
