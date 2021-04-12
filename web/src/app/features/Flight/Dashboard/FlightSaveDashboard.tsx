import React, { useEffect } from 'react';
import { observer } from 'mobx-react-lite';
import { Container, Header, Segment } from 'semantic-ui-react';
import { useStore } from '../../../store/store';
import FlightSaveSearchForm from '../Form/FlightSaveSearchForm';
import FlightSaveTable from '../Table/FlightSaveTable';

const FlightSaveDashboard = () => {
    const { flightStore } = useStore();
    const { loadAirports, savedFlights, getSavedFlights } = flightStore;

    useEffect(() => { loadAirports(); getSavedFlights(); }, [flightStore]);

    return (
        <>
            <Segment inverter textAlign="center" vertical className="flight">

                <Container vertical className="container">
                    <Header as="h2" inverted>
                        Search Saved Flight Offers
                    </Header>
                    <FlightSaveSearchForm />
                </Container>

            </Segment>
            <Segment inverter textAlign="center" vertical className="flight">

                <Container vertical className="container">
                    <FlightSaveTable flights={savedFlights} />
                </Container>

            </Segment>
        </>
    );
}

export default observer(FlightSaveDashboard);