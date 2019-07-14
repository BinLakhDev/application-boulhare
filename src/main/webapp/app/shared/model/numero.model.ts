export const enum Status {
  UTILISER = 'UTILISER',
  NONUTILUSER = 'NONUTILUSER',
  DEPASSER = 'DEPASSER',
  ANNULER = 'ANNULER'
}

export interface INumero {
  id?: number;
  numero?: string;
  statuts?: Status;
  agenceId?: number;
  utilisateurFullname?: string;
  utilisateurId?: number;
  clientInstantaneId?: number;
}

export const defaultValue: Readonly<INumero> = {};
