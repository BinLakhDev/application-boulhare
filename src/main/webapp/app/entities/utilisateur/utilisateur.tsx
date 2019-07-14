import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  openFile,
  byteSize,
  Translate,
  ICrudGetAllAction,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './utilisateur.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IUtilisateurProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IUtilisateurState = IPaginationBaseState;

export class Utilisateur extends React.Component<IUtilisateurProps, IUtilisateurState> {
  state: IUtilisateurState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { utilisateurList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="utilisateur-heading">
          <Translate contentKey="boulhareApp.utilisateur.home.title">Utilisateurs</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="boulhareApp.utilisateur.home.createLabel">Create new Utilisateur</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive striped hover>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fullname')}>
                  <Translate contentKey="boulhareApp.utilisateur.fullname">Fullname</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('username')}>
                  <Translate contentKey="boulhareApp.utilisateur.username">Username</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('password')}>
                  <Translate contentKey="boulhareApp.utilisateur.password">Password</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('logo')}>
                  <Translate contentKey="boulhareApp.utilisateur.logo">Logo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('iban')}>
                  <Translate contentKey="boulhareApp.utilisateur.iban">Iban</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('numero')}>
                  <Translate contentKey="boulhareApp.utilisateur.numero">Numero</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('email')}>
                  <Translate contentKey="boulhareApp.utilisateur.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="boulhareApp.utilisateur.agence">Agence</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="boulhareApp.utilisateur.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {utilisateurList.map((utilisateur, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${utilisateur.id}`} color="link" size="sm">
                      {utilisateur.id}
                    </Button>
                  </td>
                  <td>{utilisateur.fullname}</td>
                  <td>{utilisateur.username}</td>
                  <td>{utilisateur.password}</td>
                  <td>
                    {utilisateur.logo ? (
                      <div>
                        <a onClick={openFile(utilisateur.logoContentType, utilisateur.logo)}>
                          <img src={`data:${utilisateur.logoContentType};base64,${utilisateur.logo}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {utilisateur.logoContentType}, {byteSize(utilisateur.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{utilisateur.iban}</td>
                  <td>{utilisateur.numero}</td>
                  <td>{utilisateur.email}</td>
                  <td>{utilisateur.agenceId ? <Link to={`agence/${utilisateur.agenceId}`}>{utilisateur.agenceId}</Link> : ''}</td>
                  <td>{utilisateur.userEmail ? utilisateur.userEmail : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${utilisateur.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${utilisateur.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${utilisateur.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ utilisateur }: IRootState) => ({
  utilisateurList: utilisateur.entities,
  totalItems: utilisateur.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Utilisateur);
