import { observer } from 'mobx-react-lite';
import React from 'react';
import { useStore } from '../../store/store';
import * as Yup from 'yup';
import { ErrorMessage, Form, Formik } from 'formik';
import { AxiosError } from 'axios';
import { Button, Header, Label } from 'semantic-ui-react';
import CustomTextInput from '../../common/form/CustomTextInput';
import { toast } from 'react-toastify';

const RegisterForm = () => {
    const { modalStore, userStore } = useStore();

    if (userStore.isLoggedIn) modalStore.closeModal();

    const initialValues = {
        'username': '',
        'password': '',
        'passwordConfirm': '',
        'email': '',
        errorUsername: null,
        errorPassword: null,
        errorEmail: null
    }

    const validationSchema = {
        username:
            Yup.string()
                .required('Username is required!')
                .min(5, 'Username must be between 6 and 50 characters!')
                .max(50, 'Username must be between 6 and 50 characters!')
                .matches(/^[A-Za-z0-9]+$/, 'Username accepts only alphanumeric letters without any white space!'),
        password:
            Yup.string()
                .required('Password is required!')
                .min(8, 'Password must be between 8 and 80 characters!')
                .max(80, 'Password must be between 8 and 80 characters!')
                .matches(/[A-Za-z0-9]+$/, 'Password only accepts alphanumeric letters!'),
        passwordConfirm:
            Yup.string().oneOf([Yup.ref('password'), null], 'Passwords must match!'),
        email:
            Yup.string()
                .required('E-mail address is required!')
                .email('E-mail address is in incorrect format!')
    }

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={Yup.object(validationSchema)}
            onSubmit={(values, { setErrors }) => userStore.register(values)
                .catch((error: AxiosError) => {
                    const { data, status } = error.response!;

                    switch (status) {
                        case 400:
                            if (data.username) setErrors({ errorUsername: data.username });
                            if (data.password) setErrors({ errorPassword: data.password });
                            if (data.email) setErrors({ errorEmail: data.email });
                            break;
                        case 500:
                            console.error(data);
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
                    <Header
                        as='h2'
                        content='Register'
                        color='teal'
                        textAlign='center'
                    />

                    <CustomTextInput
                        name='username'
                        placeholder='Username'
                    />

                    <ErrorMessage
                        name='errorUsername'
                        render={() => (<><Label basic color='red' content={errors.errorUsername} /><br></br></>)}
                    />

                    <CustomTextInput
                        type='email'
                        name='email'
                        placeholder='E-mail address'
                    />

                    <ErrorMessage
                        name='errorEmail'
                        render={() => (<><Label basic color='red' content={errors.errorEmail} /><br></br></>)}
                    />

                    <CustomTextInput
                        type='password'
                        name='password'
                        placeholder='Password' />

                    <ErrorMessage
                        name='errorPassword'
                        render={() => (<><Label basic color='red' content={errors.errorPassword} /><br></br></>)}
                    />

                    <CustomTextInput
                        type='password'
                        name='passwordConfirm'
                        placeholder='Password Confirmation' />

                    <ErrorMessage
                        name='errorPasswordConfirm'
                        render={() => <Label basic color='red' content={errors.errorPassword} />}
                    />

                    <Button
                        disabled={!isValid || !dirty || isSubmitting}
                        loading={isSubmitting}
                        positive
                        content='Register'
                        type='submit'
                        fluid
                    />

                    <Button
                        onClick={modalStore.closeModal}
                        content='Cancel'
                        type='submit'
                        fluid
                    />
                </Form>
            )}
        </Formik>
    );
}

export default observer(RegisterForm);