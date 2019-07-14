import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './utilisateur.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUtilisateurDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UtilisateurDetail extends React.Component<IUtilisateurDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { utilisateurEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="boulhareApp.utilisateur.detail.title">Utilisateur</Translate> [<b>{utilisateurEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fullname">
                <Translate contentKey="boulhareApp.utilisateur.fullname">Fullname</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.fullname}</dd>
            <dt>
              <span id="username">
                <Translate contentKey="boulhareApp.utilisateur.username">Username</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.username}</dd>
            <dt>
              <span id="password">
                <Translate contentKey="boulhareApp.utilisateur.password">Password</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.password}</dd>
            <dt>
              <span id="logo">
                <Translate contentKey="boulhareApp.utilisateur.logo">Logo</Translate>
              </span>
            </dt>
            <dd>
              {utilisateurEntity.logo ? (
                <div>
                  <a onClick={openFile(utilisateurEntity.logoContentType, utilisateurEntity.logo)}>
                    <img src={`data:${utilisateurEntity.logoContentType};base64,${utilisateurEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {utilisateurEntity.logoContentType}, {byteSize(utilisateurEntity.logo)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="iban">
                <Translate contentKey="boulhareApp.utilisateur.iban">Iban</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.iban}</dd>
            <dt>
              <span id="numero">
                <Translate contentKey="boulhareApp.utilisateur.numero">Numero</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.numero}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="boulhareApp.utilisateur.email">Email</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.email}</dd>
            <dt>
              <Translate contentKey="boulhareApp.utilisateur.agence">Agence</Translate>
            </dt>
            <dd>{utilisateurEntity.agenceId ? utilisateurEntity.agenceId : ''}</dd>
            <dt>
              <Translate contentKey="boulhareApp.utilisateur.user">User</Translate>
            </dt>
            <dd>{utilisateurEntity.userEmail ? utilisateurEntity.userEmail : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/utilisateur" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/utilisateur/${utilisateurEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ utilisateur }: IRootState) => ({
  utilisateurEntity: utilisateur.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UtilisateurDetail);
