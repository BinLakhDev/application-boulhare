import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import clientInstantane, {
  ClientInstantaneState
} from 'app/entities/client-instantane/client-instantane.reducer';
// prettier-ignore
import numero, {
  NumeroState
} from 'app/entities/numero/numero.reducer';
// prettier-ignore
import utilisateur, {
  UtilisateurState
} from 'app/entities/utilisateur/utilisateur.reducer';
// prettier-ignore
import banque, {
  BanqueState
} from 'app/entities/banque/banque.reducer';
// prettier-ignore
import agence, {
  AgenceState
} from 'app/entities/agence/agence.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly clientInstantane: ClientInstantaneState;
  readonly numero: NumeroState;
  readonly utilisateur: UtilisateurState;
  readonly banque: BanqueState;
  readonly agence: AgenceState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  clientInstantane,
  numero,
  utilisateur,
  banque,
  agence,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
