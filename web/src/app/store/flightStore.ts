import { makeAutoObservable, runInAction } from 'mobx';
import agent from '../api/agent';
import { airportIATAOptions } from '../common/options/AirportOptions';
import { Flight, FlightSearchFormValues } from '../models/flight';
import { toast } from 'react-toastify';

export default class FlightStore {
    flights: Flight[] = [];
    savedFlights : Flight[] = [];
    loading = false;
    loadingInitial = false;
    loadingSearch = false;

    filteredAirports = airportIATAOptions.filter((val, id, array) =>
        array.findIndex(item => (item.iata === val.iata)) === id
    ).sort((a, b) => (a.iata > b.iata ? 1 : -1));

    airportOptions = this.filteredAirports.map((airport, id) => ({
        text: airport.iata + ' - ' + airport.name,
        value: airport.iata
    }));

    constructor() {
        makeAutoObservable(this)
    }

    deleteSelectedSavedFlight = async (id: number) => {
        this.loading = true;

        try {
            await agent.FlightAgent.deleteSave(id);

            runInAction(() => {
                this.savedFlights = this.savedFlights.filter(flight => flight.id !== id);
                this.loading = false;
                toast.success("Deleted a Saved Flight Offer!");
            });
        }
        catch (error) {
            console.error(error);
            toast.error('Error has occured! See console log!');

            this.loading = false;
        }
    }

    getSavedFlights = async () => {
        this.loadingInitial = true;

        try {
            const flights = await agent.FlightAgent.getAllSave();

            runInAction(() => {
                this.savedFlights = flights
            });

            this.loadingInitial = false;
        }
        catch (error) {
            console.error(error);
            toast.error("Error has occured. See console log!");

            this.loadingInitial = false;
        }
    }

    saveSelectedFlight = async (id: number) => {
        this.loading = true;

        try {
            const selectedFlight = this.flights.find(flight => flight.id === id);
            const flight = await agent.FlightAgent.save(selectedFlight!);

            runInAction(() => {
                this.flights = this.flights.filter(flight => flight.id !== id);
                this.loading = false;
                toast.success("Saved a Flight Offer!");
            });
        }
        catch (error) {
            console.error(error);
            toast.error('Error has occured! See console log!');

            this.loading = false;
        }
    }

    searchFlights = async (search: FlightSearchFormValues) => {
        this.loadingSearch = true;

        try {
            const flights = await agent.FlightAgent.search(search);

            runInAction(() => {
                this.flights = flights
            });

            this.loadingSearch = false;

            if (flights.length > 0) toast.info("Found " + flights.length + " result(s)!");
            else toast.info("No restults were found!");
        }
        catch (error) {
            this.loadingSearch = false;
            throw error;
        }
    }

    searchSavedFlights = async (search: FlightSearchFormValues) => {
        this.loadingSearch = true;

        try {
            const flights = await agent.FlightAgent.searchSave(search);

            runInAction(() => {
                this.savedFlights = flights
            })

            this.loadingSearch = false;

            if (flights.length > 0) toast.info("Found " + flights.length + " result(s)!");
            else toast.info("No results were found!");
        }
        catch (error) {
            this.loadingSearch = false;
            throw error;
        }
    }
}