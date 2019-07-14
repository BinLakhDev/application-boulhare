import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAgence } from 'app/shared/model/agence.model';
import { getEntities as getAgences } from 'app/entities/agence/agence.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { IClientInstantane } from 'app/shared/model/client-instantane.model';
import { getEntities as getClientInstantanes } from 'app/entities/client-instantane/client-instantane.reducer';
import { getEntity, updateEntity, createEntity, reset } from './numero.reducer';
import { INumero } from 'app/shared/model/numero.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INumeroUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INumeroUpdateState {
  isNew: boolean;
  agenceId: string;
  utilisateurId: string;
  clientInstantaneId: string;
}

export class NumeroUpdate extends React.Component<INumeroUpdateProps, INumeroUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      agenceId: '0',
      utilisateurId: '0',
      clientInstantaneId: '0',
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

    this.props.getAgences();
    this.props.getUtilisateurs();
    this.props.getClientInstantanes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { numeroEntity } = this.props;
      const entity = {
        ...numeroEntity,
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
    this.props.history.push('/entity/numero');
  };

  render() {
    const { numeroEntity, agences, utilisateurs, clientInstantanes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="boulhareApp.numero.home.createOrEditLabel">
              <Translate contentKey="boulhareApp.numero.home.createOrEditLabel">Create or edit a Numero</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : numeroEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="numero-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="numeroLabel" for="numero">
                    <Translate contentKey="boulhareApp.numero.numero">Numero</Translate>
                  </Label>
                  <AvField
                    id="numero-numero"
                    type="text"
                    name="numero"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statutsLabel">
                    <Translate contentKey="boulhareApp.numero.statuts">Statuts</Translate>
                  </Label>
                  <AvInput
                    id="numero-statuts"
                    type="select"
                    className="form-control"
                    name="statuts"
                    value={(!isNew && numeroEntity.statuts) || 'UTILISER'}
                  >
                    <option value="UTILISER">
                      <Translate contentKey="boulhareApp.Status.UTILISER" />
                    </option>
                    <option value="NONUTILUSER">
                      <Translate contentKey="boulhareApp.Status.NONUTILUSER" />
                    </option>
                    <option value="DEPASSER">
                      <Translate contentKey="boulhareApp.Status.DEPASSER" />
                    </option>
                    <option value="ANNULER">
                      <Translate contentKey="boulhareApp.Status.ANNULER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="agence.id">
                    <Translate contentKey="boulhareApp.numero.agence">Agence</Translate>
                  </Label>
                  <AvInput id="numero-agence" type="select" className="form-control" name="agenceId">
                    <option value="" key="0" />
                    {agences
                      ? agences.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="utilisateur.fullname">
                    <Translate contentKey="boulhareApp.numero.utilisateur">Utilisateur</Translate>
                  </Label>
                  <AvInput id="numero-utilisateur" type="select" className="form-control" name="utilisateurId">
                    <option value="" key="0" />
                    {utilisateurs
                      ? utilisateurs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.fullname}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="clientInstantane.id">
                    <Translate contentKey="boulhareApp.numero.clientInstantane">Client Instantane</Translate>
                  </Label>
                  <AvInput id="numero-clientInstantane" type="select" className="form-control" name="clientInstantaneId">
                    <option value="" key="0" />
                    {clientInstantanes
                      ? clientInstantanes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/numero" replace color="info">
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
  agences: storeState.agence.entities,
  utilisateurs: storeState.utilisateur.entities,
  clientInstantanes: storeState.clientInstantane.entities,
  numeroEntity: storeState.numero.entity,
  loading: storeState.numero.loading,
  updating: storeState.numero.updating,
  updateSuccess: storeState.numero.updateSuccess
});

const mapDispatchToProps = {
  getAgences,
  getUtilisateurs,
  getClientInstantanes,
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
)(NumeroUpdate);
