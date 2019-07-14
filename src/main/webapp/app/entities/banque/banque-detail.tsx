import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './banque.reducer';
import { IBanque } from 'app/shared/model/banque.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBanqueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BanqueDetail extends React.Component<IBanqueDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { banqueEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="boulhareApp.banque.detail.title">Banque</Translate> [<b>{banqueEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nameBanque">
                <Translate contentKey="boulhareApp.banque.nameBanque">Name Banque</Translate>
              </span>
            </dt>
            <dd>{banqueEntity.nameBanque}</dd>
            <dt>
              <span id="adresseSiege">
                <Translate contentKey="boulhareApp.banque.adresseSiege">Adresse Siege</Translate>
              </span>
            </dt>
            <dd>{banqueEntity.adresseSiege}</dd>
            <dt>
              <span id="telSiege">
                <Translate contentKey="boulhareApp.banque.telSiege">Tel Siege</Translate>
              </span>
            </dt>
            <dd>{banqueEntity.telSiege}</dd>
            <dt>
              <span id="codeBanque">
                <Translate contentKey="boulhareApp.banque.codeBanque">Code Banque</Translate>
              </span>
            </dt>
            <dd>{banqueEntity.codeBanque}</dd>
            <dt>
              <span id="logo">
                <Translate contentKey="boulhareApp.banque.logo">Logo</Translate>
              </span>
            </dt>
            <dd>
              {banqueEntity.logo ? (
                <div>
                  <a onClick={openFile(banqueEntity.logoContentType, banqueEntity.logo)}>
                    <img src={`data:${banqueEntity.logoContentType};base64,${banqueEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {banqueEntity.logoContentType}, {byteSize(banqueEntity.logo)}
                  </span>
                </div>
              ) : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/banque" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/banque/${banqueEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ banque }: IRootState) => ({
  banqueEntity: banque.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BanqueDetail);
