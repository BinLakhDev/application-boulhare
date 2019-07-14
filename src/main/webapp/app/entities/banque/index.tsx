import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Banque from './banque';
import BanqueDetail from './banque-detail';
import BanqueUpdate from './banque-update';
import BanqueDeleteDialog from './banque-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BanqueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BanqueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BanqueDetail} />
      <ErrorBoundaryRoute path={match.url} component={Banque} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BanqueDeleteDialog} />
  </>
);

export default Routes;
