import { observer } from 'mobx-react-lite';
import React from 'react';
import { useStore } from '../../store/store';
import { Button, Grid, Header, Segment } from 'semantic-ui-react';

const LogoutForm = () => {
    const { modalStore, userStore } = useStore();

    if (!userStore.isLoggedIn) modalStore.closeModal();

    return (
        <>
            <Segment className='ui form'>
                <Header
                    as='h2'
                    content='Do you want to sign out?'
                    color='teal'
                    textAlign='center'
                />

                <Grid centered>
                    <Grid.Row>
                        <Grid.Column>
                            <Button.Group size='huge' widths='3'>
                                <Button onClick={userStore.logout} positive>
                                    Logout
                                </Button>
                                <Button.Or />
                                <Button onClick={modalStore.closeModal} secondary>
                                    Cancel
                                </Button>
                            </Button.Group>
                        </Grid.Column>
                    </Grid.Row>
                </Grid>
            </Segment>
        </>
    );
}

export default observer(LogoutForm);