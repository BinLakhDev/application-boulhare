import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAgence } from 'app/shared/model/agence.model';
import { getEntities as getAgences } from 'app/entities/agence/agence.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './utilisateur.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUtilisateurUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUtilisateurUpdateState {
  isNew: boolean;
  agenceId: string;
  userId: string;
}

export class UtilisateurUpdate extends React.Component<IUtilisateurUpdateProps, IUtilisateurUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      agenceId: '0',
      userId: '0',
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
    this.props.getUsers();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { utilisateurEntity } = this.props;
      const entity = {
        ...utilisateurEntity,
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
    this.props.history.push('/entity/utilisateur');
  };

  render() {
    const { utilisateurEntity, agences, users, loading, updating } = this.props;
    const { isNew } = this.state;

    const { logo, logoContentType } = utilisateurEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="boulhareApp.utilisateur.home.createOrEditLabel">
              <Translate contentKey="boulhareApp.utilisateur.home.createOrEditLabel">Create or edit a Utilisateur</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : utilisateurEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="utilisateur-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fullnameLabel" for="fullname">
                    <Translate contentKey="boulhareApp.utilisateur.fullname">Fullname</Translate>
                  </Label>
                  <AvField
                    id="utilisateur-fullname"
                    type="text"
                    name="fullname"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="usernameLabel" for="username">
                    <Translate contentKey="boulhareApp.utilisateur.username">Username</Translate>
                  </Label>
                  <AvField
                    id="utilisateur-username"
                    type="text"
                    name="username"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="passwordLabel" for="password">
                    <Translate contentKey="boulhareApp.utilisateur.password">Password</Translate>
                  </Label>
                  <AvField
                    id="utilisateur-password"
                    type="text"
                    name="password"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="logoLabel" for="logo">
                      <Translate contentKey="boulhareApp.utilisateur.logo">Logo</Translate>
                    </Label>
                    <br />
                    {logo ? (
                      <div>
                        <a onClick={openFile(logoContentType, logo)}>
                          <img src={`data:${logoContentType};base64,${logo}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {logoContentType}, {byteSize(logo)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('logo')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_logo" type="file" onChange={this.onBlobChange(true, 'logo')} accept="image/*" />
                    <AvInput
                      type="hidden"
                      name="logo"
                      value={logo}
                      validate={{
                        required: { value: true, errorMessage: translate('entity.validation.required') }
                      }}
                    />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="ibanLabel" for="iban">
                    <Translate contentKey="boulhareApp.utilisateur.iban">Iban</Translate>
                  </Label>
                  <AvField
                    id="utilisateur-iban"
                    type="text"
                    name="iban"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="numeroLabel" for="numero">
                    <Translate contentKey="boulhareApp.utilisateur.numero">Numero</Translate>
                  </Label>
                  <AvField
                    id="utilisateur-numero"
                    type="text"
                    name="numero"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    <Translate contentKey="boulhareApp.utilisateur.email">Email</Translate>
                  </Label>
                  <AvField id="utilisateur-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label for="agence.id">
                    <Translate contentKey="boulhareApp.utilisateur.agence">Agence</Translate>
                  </Label>
                  <AvInput id="utilisateur-agence" type="select" className="form-control" name="agenceId">
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
                  <Label for="user.email">
                    <Translate contentKey="boulhareApp.utilisateur.user">User</Translate>
                  </Label>
                  <AvInput id="utilisateur-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.email}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/utilisateur" replace color="info">
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
  users: storeState.userManagement.users,
  utilisateurEntity: storeState.utilisateur.entity,
  loading: storeState.utilisateur.loading,
  updating: storeState.utilisateur.updating,
  updateSuccess: storeState.utilisateur.updateSuccess
});

const mapDispatchToProps = {
  getAgences,
  getUsers,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UtilisateurUpdate);
