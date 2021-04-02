export interface Flight {
    id: number,
    originLocationCode: string;
    destinationLocationCode: string;
    departureDate: Date,
    returnDate?: Date | null,
    passengerCount: number,
    transferCount: number,
    currencyCode: string,
    totalPrice: number
}

export class Flight implements Flight {
    constructor(init?: FlightFormValues) {
        Object.assign(this, init);
    }
}

export class FlightFormValues {
    originLocationCode: string = '';
    destinationLocationCode: string = '';
    departureDate: Date | null = null;
    returnDate?: Date | null = null;
    passengerCount: number = 0;
    transferCount: number = 0
    currencyCode: string = '';
    totalPrice: number = 0;

    constructor(flight: Flight) {
        this.originLocationCode = flight.originLocationCode;
        this.destinationLocationCode = flight.destinationLocationCode;
        this.departureDate = flight.departureDate;
        this.returnDate = flight.returnDate;
        this.passengerCount = flight.passengerCount;
        this.transferCount = flight.transferCount;
        this.currencyCode = flight.currencyCode;
        this.totalPrice = flight.totalPrice;
    }
}

export interface FlightSearchFormValues {
    originLocationCode: string;
    destinationLocationCode: string;
    departureDate: Date | null;
    returnDate: Date | null;
    passengerCount: number;
    currencyCode: string;
}