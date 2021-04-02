import { observer } from 'mobx-react-lite';
import { Link } from 'react-router-dom';
import { Button, Grid, Label } from 'semantic-ui-react';
import { useStore } from '../../../store/store';
import { Formik, Form, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { currencyOptions } from '../../../common/options/CurrencyOptions';
import React from 'react';
import CustomDateInput from '../../../common/form/CustomDateInput';
import CustomTextInput from '../../../common/form/CustomTextInput';
import CustomSelectInput from '../../../common/form/CustomSelectInput';
import { AxiosError } from 'axios';
import { toast } from 'react-toastify';

const FlightSearchForm = () => {
    const { flightStore } = useStore();
    const { airportOptions, loadingInitial, searchFlights } = flightStore;

    let newDate = new Date();
    newDate.setDate(newDate.getDate() + 1 );

    const initialValues = {
        'originLocationCode': '',
        'destinationLocationCode': '',
        'departureDate': newDate,
        'returnDate': null,
        'currencyCode': '',
        'passengerCount': 1,
        errorOrigin: null,
        errorDestination: null,
        errorDeparture: null,
        errorReturn: null,
        errorPassenger: null,
        errorCurrency: null
    }

    const validationSchema = {
        originLocationCode:
            Yup.string()
                .required('Origin Location Code is required!'),
        destinationLocationCode:
            Yup.string()
                .required('Destination Location Code is required!'),
        passengerCount:
            Yup.number()
                .integer('Passenger Count must be integer number!')
                .required('Passenger Count is required!')
                .moreThan(0, 'Passenger Count must be between 1 and 9!')
                .lessThan(10, 'Passenger Count must be between 1 and 9!'),
        departureDate:
            Yup.date()
                .required('Departure Date is required!')
    }

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={Yup.object(validationSchema)}
            onSubmit={(values, { setErrors }) => searchFlights(values)
                .catch((error: AxiosError) => {
                    const { data, status } = error.response!;

                    switch (status) {
                        case 400:
                            if (data.originLocationCode) setErrors({ errorOrigin: data.originLocationCode });
                            if (data.destinationLocationCode) setErrors({ errorDestination: data.destinationLocationCode });
                            if (data.departureDate) setErrors({ errorDeparture: data.departureDate });
                            if (data.departureDate) setErrors({ errorReturn: data.returnDate });
                            if (data.validReturnDate) setErrors({ errorReturn: data.validReturnDate });
                            if (data.passengerCount) setErrors({ errorPassenger: data.passengerCount });
                            if (data.currencyCode) setErrors({ errorCurrency: data.currencyCode });
                            break;
                        case 403:
                            toast.error("Unauthorized access!");
                            break;
                        case 500:
                            console.log(data);
                            toast.error('Internal server error! See console log!')
                            break;
                    }
                })}
        >
            {({ handleSubmit, isSubmitting, errors, isValid, dirty }) => (
                <Form
                    className='ui form'
                    onSubmit={handleSubmit}
                    autoComplete='off'
                >
                    <Grid>
                        <Grid.Row columns={3} centered>

                            <Grid.Column>
                                <CustomSelectInput options={airportOptions} placeholder='Origin Location Code' name='originLocationCode' />
                                <ErrorMessage
                                    name='errorOrigin'
                                    render={() => (<Label basic color='red' content={errors.errorOrigin} />)} />
                            </Grid.Column>

                            <Grid.Column>
                                <CustomSelectInput options={airportOptions} placeholder='Destination Location Code' name='destinationLocationCode' />
                                <ErrorMessage
                                    name='errorDestination'
                                    render={() => (<Label basic color='red' content={errors.errorDestination} />)} />
                            </Grid.Column>

                            <Grid.Column>
                                <CustomTextInput type='number' name='passengerCount' placeholder='Passenger Count' />
                                <ErrorMessage
                                    name='errorPassenger'
                                    render={() => (<Label basic color='red' content={errors.errorPassenger} />)} />
                            </Grid.Column>

                        </Grid.Row>

                        <Grid.Row columns={3} centered>

                            <Grid.Column>
                                <CustomDateInput name='departureDate' title='Departure Date' minDate={newDate} />
                                <ErrorMessage
                                    name='errorDeparture'
                                    render={() => (<Label basic color='red' content={errors.errorDeparture} />)} />
                            </Grid.Column>

                            <Grid.Column>
                                <CustomDateInput name='returnDate' title='Return Date' minDate={newDate} />
                                <ErrorMessage
                                    name='errorReturn'
                                    render={() => (<Label basic color='red' content={errors.errorReturn} />)} />
                            </Grid.Column>

                            <Grid.Column>
                                <CustomSelectInput options={currencyOptions} name='currencyCode' placeholder='Currency' />
                                <ErrorMessage
                                    name='errorCurrency'
                                    render={() => (<Label basic color='red' content={errors.errorCurrency} />)} />
                            </Grid.Column>

                        </Grid.Row>

                        <Grid.Row columns={1}>

                            <Grid.Column centered>

                                <Button.Group size='huge' widths='3'>
                                    <Button
                                        disabled={!isValid || !dirty || isSubmitting || loadingInitial}
                                        loading={isSubmitting || loadingInitial}
                                        content='Search'
                                        type='submit'
                                        primary
                                    />
                                    <Button as={Link} to='/saves' inverted>
                                        Search Saves
                                    </Button>
                                    <Button as={Link} to='/' inverted>
                                        Go Back
                                    </Button>
                                </Button.Group>
                                
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                </Form>
            )}
        </Formik>
    );
}

export default observer(FlightSearchForm);