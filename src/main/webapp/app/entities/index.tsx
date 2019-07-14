import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientInstantane from './client-instantane';
import Numero from './numero';
import Utilisateur from './utilisateur';
import Banque from './banque';
import Agence from './agence';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/client-instantane`} component={ClientInstantane} />
      <ErrorBoundaryRoute path={`${match.url}/numero`} component={Numero} />
      <ErrorBoundaryRoute path={`${match.url}/utilisateur`} component={Utilisateur} />
      <ErrorBoundaryRoute path={`${match.url}/banque`} component={Banque} />
      <ErrorBoundaryRoute path={`${match.url}/agence`} component={Agence} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
