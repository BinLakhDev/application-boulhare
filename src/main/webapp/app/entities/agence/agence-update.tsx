import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBanque } from 'app/shared/model/banque.model';
import { getEntities as getBanques } from 'app/entities/banque/banque.reducer';
import { getEntity, updateEntity, createEntity, reset } from './agence.reducer';
import { IAgence } from 'app/shared/model/agence.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAgenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAgenceUpdateState {
  isNew: boolean;
  banqueId: string;
}

export class AgenceUpdate extends React.Component<IAgenceUpdateProps, IAgenceUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      banqueId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getBanques();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { agenceEntity } = this.props;
      const entity = {
        ...agenceEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/agence');
  };

  render() {
    const { agenceEntity, banques, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="boulhareApp.agence.home.createOrEditLabel">
              <Translate contentKey="boulhareApp.agence.home.createOrEditLabel">Create or edit a Agence</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : agenceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="agence-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeAgenceLabel" for="codeAgence">
                    <Translate contentKey="boulhareApp.agence.codeAgence">Code Agence</Translate>
                  </Label>
                  <AvField
                    id="agence-codeAgence"
                    type="text"
                    name="codeAgence"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="longitudeLabel" for="longitude">
                    <Translate contentKey="boulhareApp.agence.longitude">Longitude</Translate>
                  </Label>
                  <AvField
                    id="agence-longitude"
                    type="text"
                    name="longitude"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="latitudeLabel" for="latitude">
                    <Translate contentKey="boulhareApp.agence.latitude">Latitude</Translate>
                  </Label>
                  <AvField
                    id="agence-latitude"
                    type="text"
                    name="latitude"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="adresseAgenceLabel" for="adresseAgence">
                    <Translate contentKey="boulhareApp.agence.adresseAgence">Adresse Agence</Translate>
                  </Label>
                  <AvField
                    id="agence-adresseAgence"
                    type="text"
                    name="adresseAgence"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="telSiegeLabel" for="telSiege">
                    <Translate contentKey="boulhareApp.agence.telSiege">Tel Siege</Translate>
                  </Label>
                  <AvField
                    id="agence-telSiege"
                    type="text"
                    name="telSiege"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="banque.nameBanque">
                    <Translate contentKey="boulhareApp.agence.banque">Banque</Translate>
                  </Label>
                  <AvInput id="agence-banque" type="select" className="form-control" name="banqueId">
                    <option value="" key="0" />
                    {banques
                      ? banques.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nameBanque}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/agence" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  banques: storeState.banque.entities,
  agenceEntity: storeState.agence.entity,
  loading: storeState.agence.loading,
  updating: storeState.agence.updating,
  updateSuccess: storeState.agence.updateSuccess
});

const mapDispatchToProps = {
  getBanques,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AgenceUpdate);
