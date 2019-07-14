import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './numero.reducer';
import { INumero } from 'app/shared/model/numero.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INumeroDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NumeroDetail extends React.Component<INumeroDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { numeroEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="boulhareApp.numero.detail.title">Numero</Translate> [<b>{numeroEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="numero">
                <Translate contentKey="boulhareApp.numero.numero">Numero</Translate>
              </span>
            </dt>
            <dd>{numeroEntity.numero}</dd>
            <dt>
              <span id="statuts">
                <Translate contentKey="boulhareApp.numero.statuts">Statuts</Translate>
              </span>
            </dt>
            <dd>{numeroEntity.statuts}</dd>
            <dt>
              <Translate contentKey="boulhareApp.numero.agence">Agence</Translate>
            </dt>
            <dd>{numeroEntity.agenceId ? numeroEntity.agenceId : ''}</dd>
            <dt>
              <Translate contentKey="boulhareApp.numero.utilisateur">Utilisateur</Translate>
            </dt>
            <dd>{numeroEntity.utilisateurFullname ? numeroEntity.utilisateurFullname : ''}</dd>
            <dt>
              <Translate contentKey="boulhareApp.numero.clientInstantane">Client Instantane</Translate>
            </dt>
            <dd>{numeroEntity.clientInstantaneId ? numeroEntity.clientInstantaneId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/numero" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/numero/${numeroEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ numero }: IRootState) => ({
  numeroEntity: numero.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NumeroDetail);
