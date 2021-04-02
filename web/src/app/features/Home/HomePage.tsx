import React from 'react';
import { Link } from 'react-router-dom';
import { Button, Container, Header, Segment } from 'semantic-ui-react';
import { useStore } from '../../store/store';
import LoginForm from '../User/LoginForm';
import LogoutForm from '../User/LogoutForm';
import RegisterForm from '../User/RegisterForm';

const HomePage = () => {
    const { modalStore, userStore } = useStore();

    return (
        <Segment inverter textAlign='center' vertical className='homepage'>
            <Container className='container' vertical>
                <Header as='h1' inverted>
                    Flight Finder
                </Header>
                <Header as='h2' inverted>
                    By Mario Sušac
                </Header>
                {userStore.isLoggedIn ? (
                    <>
                        <Header as='h2' inverted>
                            You are logged as: {userStore.user?.username}
                        </Header>

                        <Button.Group size='huge' widths='3'>
                            <Button as={Link} to='/flights' inverted>
                                Search Flight Offers
                            </Button>

                            <Button as={Link} to='/saves' inverted>
                                Search Saves
                            </Button>     

                            <Button onClick={() => modalStore.openModal(<LogoutForm />)} inverted>
                                Logout
                            </Button>
                        </Button.Group>
                    </>
                )
                    :
                    (
                        <>
                            <Button.Group size='huge' widths='3'>
                                <Button
                                    onClick={() => modalStore.openModal(<LoginForm />)} inverted>
                                    Login
                                </Button>

                                <Button.Or />

                                <Button
                                    onClick={() => modalStore.openModal(<RegisterForm />)} inverted>
                                    Register
                                </Button>
                            </Button.Group>
                        </>
                    )}
            </Container>
        </Segment>
    )
}

export default HomePage;