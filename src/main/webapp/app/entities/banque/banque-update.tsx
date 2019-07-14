import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './banque.reducer';
import { IBanque } from 'app/shared/model/banque.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBanqueUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBanqueUpdateState {
  isNew: boolean;
}

export class BanqueUpdate extends React.Component<IBanqueUpdateProps, IBanqueUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { banqueEntity } = this.props;
      const entity = {
        ...banqueEntity,
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
    this.props.history.push('/entity/banque');
  };

  render() {
    const { banqueEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { logo, logoContentType } = banqueEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="boulhareApp.banque.home.createOrEditLabel">
              <Translate contentKey="boulhareApp.banque.home.createOrEditLabel">Create or edit a Banque</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : banqueEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="banque-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameBanqueLabel" for="nameBanque">
                    <Translate contentKey="boulhareApp.banque.nameBanque">Name Banque</Translate>
                  </Label>
                  <AvField
                    id="banque-nameBanque"
                    type="text"
                    name="nameBanque"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="adresseSiegeLabel" for="adresseSiege">
                    <Translate contentKey="boulhareApp.banque.adresseSiege">Adresse Siege</Translate>
                  </Label>
                  <AvField id="banque-adresseSiege" type="text" name="adresseSiege" />
                </AvGroup>
                <AvGroup>
                  <Label id="telSiegeLabel" for="telSiege">
                    <Translate contentKey="boulhareApp.banque.telSiege">Tel Siege</Translate>
                  </Label>
                  <AvField id="banque-telSiege" type="text" name="telSiege" />
                </AvGroup>
                <AvGroup>
                  <Label id="codeBanqueLabel" for="codeBanque">
                    <Translate contentKey="boulhareApp.banque.codeBanque">Code Banque</Translate>
                  </Label>
                  <AvField id="banque-codeBanque" type="text" name="codeBanque" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="logoLabel" for="logo">
                      <Translate contentKey="boulhareApp.banque.logo">Logo</Translate>
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
                <Button tag={Link} id="cancel-save" to="/entity/banque" replace color="info">
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
  banqueEntity: storeState.banque.entity,
  loading: storeState.banque.loading,
  updating: storeState.banque.updating,
  updateSuccess: storeState.banque.updateSuccess
});

const mapDispatchToProps = {
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
)(BanqueUpdate);
