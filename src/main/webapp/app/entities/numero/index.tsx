import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Numero from './numero';
import NumeroDetail from './numero-detail';
import NumeroUpdate from './numero-update';
import NumeroDeleteDialog from './numero-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NumeroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NumeroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NumeroDetail} />
      <ErrorBoundaryRoute path={match.url} component={Numero} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NumeroDeleteDialog} />
  </>
);

export default Routes;
