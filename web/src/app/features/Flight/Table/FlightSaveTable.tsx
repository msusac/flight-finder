import { observer } from "mobx-react-lite";
import React from "react";
import { Table } from "semantic-ui-react";
import LoadingComponent from "../../../layout/LoadingComponent";
import { Flight } from "../../../models/flight";
import { useStore } from "../../../store/store";
import FlightSaveTableItem from "./Items/FlightSaveTableItem";

interface Props {
    flights: Flight[]
}

const FlightTable = ({ flights }: Props) => {

    const { flightStore } = useStore();
    
    if (flightStore.loadingInitial) return <LoadingComponent content='Please wait...' />;
    if (flightStore.loadingSearch) return <LoadingComponent content='Searching...' />

    return (
        <Table celled striped>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell width={2}>Origin Location</Table.HeaderCell>
                    <Table.HeaderCell width={2}>Destination Location</Table.HeaderCell>
                    <Table.HeaderCell width={2}>Departure Date</Table.HeaderCell>
                    <Table.HeaderCell width={2}>Return Date</Table.HeaderCell>
                    <Table.HeaderCell width={1}>Passengers Count</Table.HeaderCell>
                    <Table.HeaderCell width={1}>Transfers Count</Table.HeaderCell>
                    <Table.HeaderCell width={1}>Price (Currency)</Table.HeaderCell>
                    <Table.HeaderCell width={1}></Table.HeaderCell>
                </Table.Row>
            </Table.Header>
            <Table.Body>
            {
                flights.map(flight => (<FlightSaveTableItem flight={flight} />))
            }
            </Table.Body>
        </Table>
    )
}

export default observer(FlightTable);