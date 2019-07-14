import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './agence.reducer';
import { IAgence } from 'app/shared/model/agence.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAgenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AgenceDetail extends React.Component<IAgenceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { agenceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="boulhareApp.agence.detail.title">Agence</Translate> [<b>{agenceEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="codeAgence">
                <Translate contentKey="boulhareApp.agence.codeAgence">Code Agence</Translate>
              </span>
            </dt>
            <dd>{agenceEntity.codeAgence}</dd>
            <dt>
              <span id="longitude">
                <Translate contentKey="boulhareApp.agence.longitude">Longitude</Translate>
              </span>
            </dt>
            <dd>{agenceEntity.longitude}</dd>
            <dt>
              <span id="latitude">
                <Translate contentKey="boulhareApp.agence.latitude">Latitude</Translate>
              </span>
            </dt>
            <dd>{agenceEntity.latitude}</dd>
            <dt>
              <span id="adresseAgence">
                <Translate contentKey="boulhareApp.agence.adresseAgence">Adresse Agence</Translate>
              </span>
            </dt>
            <dd>{agenceEntity.adresseAgence}</dd>
            <dt>
              <span id="telSiege">
                <Translate contentKey="boulhareApp.agence.telSiege">Tel Siege</Translate>
              </span>
            </dt>
            <dd>{agenceEntity.telSiege}</dd>
            <dt>
              <Translate contentKey="boulhareApp.agence.banque">Banque</Translate>
            </dt>
            <dd>{agenceEntity.banqueNameBanque ? agenceEntity.banqueNameBanque : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/agence" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/agence/${agenceEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ agence }: IRootState) => ({
  agenceEntity: agence.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AgenceDetail);
