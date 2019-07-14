import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientInstantane from './client-instantane';
import ClientInstantaneDetail from './client-instantane-detail';
import ClientInstantaneUpdate from './client-instantane-update';
import ClientInstantaneDeleteDialog from './client-instantane-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientInstantaneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientInstantaneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientInstantaneDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientInstantane} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ClientInstantaneDeleteDialog} />
  </>
);

export default Routes;
