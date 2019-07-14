import 'react-toastify/dist/ReactToastify.css';
import './app.scss';

import React from 'react';
import { connect } from 'react-redux';
import { Card, Row, Col } from 'reactstrap';
import { HashRouter as Router, Link } from 'react-router-dom';
import { ToastContainer, ToastPosition, toast } from 'react-toastify';
import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import { setLocale } from 'app/shared/reducers/locale';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';

export interface IAppProps extends StateProps, DispatchProps {}

export class App extends React.Component<IAppProps> {
  componentDidMount() {
    this.props.getSession();
    this.props.getProfile();
  }

  render() {
    const paddingTop = '60px';
    const { isAuthenticated, account } = this.props;
    return (
      <Router>
        <div className="app-container" style={{ paddingTop }}>
          <ToastContainer
            position={toast.POSITION.TOP_LEFT as ToastPosition}
            className="toastify-container"
            toastClassName="toastify-toast"
          />
          <ErrorBoundary>
            <Header
              isAuthenticated={this.props.isAuthenticated}
              isAdmin={this.props.isAdmin}
              currentLocale={this.props.currentLocale}
              onLocaleChange={this.props.setLocale}
              ribbonEnv={this.props.ribbonEnv}
              isInProduction={this.props.isInProduction}
              isSwaggerEnabled={this.props.isSwaggerEnabled}
            />
          </ErrorBoundary>
          {this.props.isAuthenticated && this.props.isAdmin ? (
            <div className="container-fluid view-container" id="app-view-container">
              {this.props.isAdmin ? (
                <Row className="rowgoche">
                  <Col md="auto" className="gauche">
                    <div className="zu">
                      <img className="imgprofile" src="https://lh3.googleusercontent.com/a/default-user=s56-c-k-no" />
                      <span className="name1">{account.email}</span>
                    </div>
                    <ul className="ul2">
                      <i className="fa fa-tasks" />
                      &nbsp; &nbsp;
                      <span className="font-weight-bold">DEMANDES</span>
                    </ul>
                    <ul className="ul">
                      <li>
                        <Link className="a" to="/entity/numero">
                          <i className="fa fa-exchange" />
                          &nbsp; &nbsp; <span>Numero</span>
                        </Link>
                      </li>
                      <li>
                        <Link className="a link" to="/entity/utilusateur">
                          <i className="fa fa-users" aria-hidden="true" />
                          &nbsp; &nbsp;
                          <span>Utilusateur</span>
                        </Link>
                      </li>
                    </ul>
                    <ul className="ul2">
                      {/* <span className="titleClass">Demandes</span> */}
                      <i className="fa fa-tasks" />
                      &nbsp; &nbsp;
                      <span className="font-weight-bold">SUPPORTS</span>
                    </ul>
                    <ul className="ul">
                      <li>
                        <Link className="a" to="/entity/banque">
                          <i className="fa fa-tasks" />
                          &nbsp; &nbsp;
                          <span> Les Banques</span>
                        </Link>
                      </li>
                      <li>
                        <Link className="a" to="/entity/agence">
                          <i className="fa fa-tasks" />
                          &nbsp; &nbsp;
                          <span> Les Agences</span>
                        </Link>
                      </li>
                    </ul>
                    <ul className="ul2">
                      <i className="fa fa-cogs" />
                      &nbsp; &nbsp;
                      <span className="font-weight-bold">PARAMETRES</span>
                    </ul>
                    <ul className="ul">
                      <li>
                        <Link className="a" to="/entity/serenity-services/manager">
                          <i className="fa fa-tasks" />
                          &nbsp; &nbsp;
                          <span> Les services</span>
                        </Link>
                      </li>
                    </ul>
                  </Col>
                  <Col class="droit">
                    <Row class="bodybody">
                      <Col ms={4} class="droit">
                        <div className="info-box">
                          <span className="info-box-icon bg-aqua">
                            <i className="fa fa-book" />
                          </span>
                          <div className="info-box-content">
                            <span className="info-box-text">Banques connecter</span>
                            <span className="info-box-number">
                              0<small>&nbsp;Total</small>
                            </span>
                          </div>
                        </div>
                      </Col>
                      <Col ms={4}>
                        <div className="info-box">
                          <span className="info-box-icon bg-red">
                            <i className="fa fa-credit-card-alt" />
                          </span>
                          <div className="info-box-content">
                            <span className="info-box-text">Agences en Ligne</span>
                            <span className="info-box-number">
                              0<small>&nbsp; total</small>
                            </span>
                          </div>
                        </div>
                      </Col>
                      <Col className="content-box" ms={4}>
                        <div className="info-box">
                          <span className="info-box-icon bg-green">
                            {/* fa fa-opera */}
                            <i className="fa fa-credit-card-alt" />
                          </span>
                          <div className="info-box-content">
                            <span className="info-box-text">Clients inscrites</span>
                            <span className="info-box-number">
                              0
                              <small>
                                &nbsp;
                                <span className="info-box-text"> total</span>
                              </small>
                            </span>
                          </div>
                        </div>
                      </Col>
                      <Col ms={4}>
                        <div className="info-box">
                          <span className="info-box-icon bg-yellow">
                            <i className="fa fa-users" aria-hidden="true" />
                          </span>
                          <div className="info-box-content">
                            <span className="info-box-text">Client Non inscrite</span>
                            <span className="info-box-number">
                              0
                              <small>
                                &nbsp;
                                <span className="info-box-text">total</span>
                              </small>
                            </span>
                          </div>
                        </div>
                      </Col>
                    </Row>
                    <Card className="jh-card">
                      <ErrorBoundary>
                        <AppRoutes />
                      </ErrorBoundary>
                    </Card>
                  </Col>
                </Row>
              ) : (
                <Row className="rowgoche">
                  <Col md="auto" class="gauche overflow-auto">
                    <div className="zu">
                      <img className="imgprofile" src="https://lh3.googleusercontent.com/a/default-user=s56-c-k-no" />
                      <span className="name1">Manager {account.login}</span>
                    </div>
                    <ul className="ul2">
                      {/* <span className="titleClass">Demandes</span> */}
                      <i className="fa fa-tasks" />
                      &nbsp; &nbsp;
                      <span className="font-weight-bold">DEMANDES</span>
                    </ul>
                    <ul className="ul">
                      <li>
                        <Link className="a" to="/entity/transfer/view/manager">
                          <i className="fa fa-exchange" />
                          &nbsp; &nbsp; <span>Virements</span>
                          {/* <Translate contentKey="global.menu.entities.coreTransfertAsk" /> */}
                        </Link>
                      </li>
                      <li>
                        <Link className="a link" to="/entity/beneficiary/view/manager">
                          <i className="fa fa-users" aria-hidden="true" />
                          &nbsp; &nbsp;
                          <span>Bénéficiaires</span>
                          {/* <Translate contentKey="global.menu.entities.coreBenefAsk" /> */}
                          {/* <span className="pull-right-container">
                          <span className="badge badge-danger">4</span>
                          </span> */}
                        </Link>
                      </li>
                      <li>
                        <Link className="a" to="/entity/check-opposition/view/manager">
                          <i className="fa fa-ban" />
                          &nbsp; &nbsp;
                          <span>Oppositions Chéquiers</span>
                          {/* <Translate contentKey="global.menu.entities.coreCardOrder" /> */}
                        </Link>
                      </li>
                      <li>
                        <Link className="a" to="/entity/card-opposition/view/manager">
                          <i className="fa fa-chain-broken" />
                          &nbsp; &nbsp;
                          <span>Oppositions carte</span>
                          {/* <Translate contentKey="global.menu.entities.coreCardOrder" /> */}
                        </Link>
                      </li>
                      <li>
                        <Link className="a" to="/entity/card-order/view/manager">
                          <i className="fa fa-credit-card-alt" />
                          &nbsp; &nbsp;
                          {/* <Translate contentKey="global.menu.entities.coreCardAsk" /> */}
                          <span>Cartes</span>
                          {/* &nbsp; &nbsp;
                          <span className="badge badge-danger">4</span> */}
                          {/* <Translate contentKey="global.menu.entities.coreCardOrder" /> */}
                        </Link>
                      </li>
                      <li>
                        <Link className="a" to="/entity/checkbook-order/view/manager">
                          <i className="fa fa-book" />
                          &nbsp; &nbsp;
                          <span>Chéquiers</span>
                          {/* <Translate contentKey="global.menu.entities.coreCheckbookOrder" /> */}
                          {/* <Translate contentKey="global.menu.entities.coreCheekbookAsk" /> */}
                        </Link>
                      </li>
                      <li>
                        <Link className="a" to="/entity/client-subscription">
                          <i className="fa fa-user-circle-o" />
                          &nbsp; &nbsp;
                          <span>Abonnements</span>
                        </Link>
                      </li>
                    </ul>
                    <ul className="ul2">
                      {/* <span className="titleClass">Demandes</span> */}
                      <i className="fa fa-tasks" />
                      &nbsp; &nbsp;
                      <span className="font-weight-bold">SUPPORTS</span>
                    </ul>
                    <ul className="ul">
                      <li>
                        <Link className="a" to="/entity/client/manager">
                          <i className="fa fa-tasks" />
                          &nbsp; &nbsp;
                          <span> Les clients</span>
                        </Link>
                      </li>
                    </ul>
                    <ul className="ul2">
                      <i className="fa fa-cogs" />
                      &nbsp; &nbsp;
                      <span className="font-weight-bold">PARAMETRES</span>
                    </ul>
                    <ul className="ul">
                      <li>
                        <Link className="a" to="/entity/serenity-services/manager">
                          <i className="fa fa-tasks" />
                          &nbsp; &nbsp;
                          <span> Les services</span>
                        </Link>
                      </li>
                      <li>
                        <Link className="a" to="/entity/pack/view/manager">
                          <i className="fa fa-cubes" />
                          &nbsp; &nbsp;
                          <span>Les Packages</span>
                        </Link>
                      </li>
                    </ul>
                  </Col>
                  <Col class="droit">
                    <section className="content-header">
                      <ol className="breadcrumb">
                        <li>
                          <i className="fa fa-dashboard" />
                          &nbsp; &nbsp;Demandes en attente
                        </li>
                      </ol>
                    </section>
                    <Row className="bodybody fieldset">
                      <Col ms={3} class="droit">
                        <Link className="abox" to="/entity/checkbook-order/view/manager">
                          <div className="info-box">
                            <span className="info-box-icon bg-aqua">
                              <i className="fa fa-book" />
                            </span>
                            <div className="info-box-content">
                              <span className="info-box-number">0</span>
                            </div>
                            &nbsp;
                            <span className="info-box-text">Chéquiers</span>
                          </div>
                        </Link>
                      </Col>
                      <Col ms={3}>
                        <Link className="abox" to="/entity/card-order/view/manager">
                          <div className="info-box">
                            <span className="info-box-icon bg-red">
                              <i className="fa fa-credit-card-alt" />
                            </span>
                            <div className="info-box-content">
                              <span className="info-box-number">0</span>
                              &nbsp;
                              <span className="info-box-text">Cartes</span>
                            </div>
                          </div>
                        </Link>
                      </Col>
                      <Col ms={3}>
                        <Link className="abox" to="/entity/beneficiary/view/manager">
                          <div className="info-box">
                            <span className="info-box-icon bg-grey">
                              <i className="fa fa-users" />
                            </span>
                            <div className="info-box-content">
                              <span className="info-box-number">0</span>
                              <span className="info-box-text">Bénéficiaires</span>
                            </div>
                          </div>
                        </Link>
                      </Col>
                      <Col className="content-box" ms={3}>
                        <Link className="abox" to="/entity/client-subscription">
                          <div className="info-box">
                            <span className="info-box-icon bg-green">
                              <i className="fa fa-chain-broken" />
                            </span>
                            <div className="info-box-content">
                              <span className="info-box-number">0</span>
                              &nbsp;
                              <span className="info-box-text">Oppositions</span>
                            </div>
                          </div>
                        </Link>
                      </Col>
                      <Col ms={3}>
                        <Link className="abox" to="/entity/client-subscription">
                          <div className="info-box">
                            <span className="info-box-icon bg-yellow">
                              <i className="fa fa-user-circle-o" aria-hidden="true" />
                            </span>
                            <div className="info-box-content">
                              <span className="info-box-number">0</span>
                              &nbsp;
                              <span className="info-box-text">Abonnements</span>
                            </div>
                          </div>
                        </Link>
                      </Col>
                    </Row>
                    <Card className="jh-card printtag print-break-page">
                      <ErrorBoundary>
                        <AppRoutes />
                      </ErrorBoundary>
                    </Card>
                  </Col>
                </Row>
              )}
            </div>
          ) : (
            <div className="container-fluid view-container" id="app-view-container">
              <AppRoutes />
            </div>
          )}
        </div>
      </Router>
    );
  }
}

const mapStateToProps = ({ authentication, applicationProfile, locale }: IRootState) => ({
  currentLocale: locale.currentLocale,
  isAuthenticated: authentication.isAuthenticated,
  account: authentication.account,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  ribbonEnv: applicationProfile.ribbonEnv,
  isInProduction: applicationProfile.inProduction,
  isSwaggerEnabled: applicationProfile.isSwaggerEnabled
});

const mapDispatchToProps = { setLocale, getSession, getProfile };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
