export interface IBanque {
  id?: number;
  nameBanque?: string;
  adresseSiege?: string;
  telSiege?: string;
  codeBanque?: string;
  logoContentType?: string;
  logo?: any;
}

export const defaultValue: Readonly<IBanque> = {};
