import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client-instantane.reducer';
import { IClientInstantane } from 'app/shared/model/client-instantane.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientInstantaneDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ClientInstantaneDetail extends React.Component<IClientInstantaneDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { clientInstantaneEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="boulhareApp.clientInstantane.detail.title">ClientInstantane</Translate> [
            <b>{clientInstantaneEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">
                <Translate contentKey="boulhareApp.clientInstantane.nom">Nom</Translate>
              </span>
            </dt>
            <dd>{clientInstantaneEntity.nom}</dd>
            <dt>
              <span id="phone">
                <Translate contentKey="boulhareApp.clientInstantane.phone">Phone</Translate>
              </span>
            </dt>
            <dd>{clientInstantaneEntity.phone}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="boulhareApp.clientInstantane.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={clientInstantaneEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="boulhareApp.clientInstantane.agence">Agence</Translate>
            </dt>
            <dd>{clientInstantaneEntity.agenceCodeAgence ? clientInstantaneEntity.agenceCodeAgence : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/client-instantane" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/client-instantane/${clientInstantaneEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ clientInstantane }: IRootState) => ({
  clientInstantaneEntity: clientInstantane.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClientInstantaneDetail);
