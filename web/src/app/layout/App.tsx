import React, { Component, useEffect } from 'react';
import './styles.css';
import { useStore } from '../store/store';
import { observer } from 'mobx-react-lite';
import { Route, Switch } from 'react-router-dom';
import LoadingComponent from './LoadingComponent';
import { ToastContainer } from 'react-toastify';
import { Container } from 'semantic-ui-react';
import ModalContainer from '../common/modals/ModalContainer';
import HomePage from '../features/Home/HomePage';
import FlightDashboard from '../features/Flight/Dashboard/FlightDashboard';
import PrivateRoute from './PrivateRoute';
import FlightSaveDashboard from '../features/Flight/Dashboard/FlightSaveDashboard';

function App() {
  const { commonStore, userStore } = useStore();

  useEffect(() => {
    if (commonStore.token) {
      userStore.getUser().finally(() => commonStore.setAppLoaded());
    }
    else {
      commonStore.setAppLoaded();
    }
  }, [commonStore, userStore]);

  if (!commonStore.appLoaded) return <LoadingComponent content='Loading ...' />

  return (
    <>
      <ToastContainer position='top-center' hideProgressBar />
      <ModalContainer />
      <Route exact path='/' component={HomePage} />
      <Route path={'/(.+)'} render={() => (
        <>
          <Container>
            <Switch>
              <PrivateRoute path='/flights' component={FlightDashboard} />
              <PrivateRoute path='/saves' component={FlightSaveDashboard} />
            </Switch>
          </Container>
        </>
      )} />
    </>
  );
}

export default observer(App);
