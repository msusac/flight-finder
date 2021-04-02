import { ErrorMessage, Form, Formik } from 'formik';
import { observer } from 'mobx-react-lite';
import React from 'react';
import { useStore } from '../../store/store';
import * as Yup from 'yup';
import { Button, Divider, Header, Label } from 'semantic-ui-react';
import { AxiosError } from 'axios';
import CustomTextInput from '../../common/form/CustomTextInput';
import { toast } from 'react-toastify';

const LoginForm = () => {
    const { modalStore, userStore } = useStore();

    if(userStore.isLoggedIn) modalStore.closeModal();

    const initialValues = {
        'username': '',
        'password': '',
        errorLogin: null,
        errorUsername: null,
        errorPassword: null
    }

    const validationSchema = {
        username: Yup.string().required('Username is required!'),
        password: Yup.string().required('Password is required!')
    }

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={Yup.object(validationSchema)}
            onSubmit={(values, { setErrors }) => userStore.login(values)
            .catch((error: AxiosError) => {
                const { data, status } = error.response!;

                switch (status) {
                    case 400:
                        if (data.username) setErrors({ errorUsername: data.username });
                        if (data.password) setErrors({ errorPassword: data.password });
                        break;
                    case 403:
                        setErrors({ errorLogin: 'Invalid Username/Password!' });
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
                        content='Login'
                        color='teal'
                        textAlign='center'
                    />

                    <ErrorMessage
                        name='errorLogin'
                        render={() => (<><Label basic color='red' content={errors.errorLogin} /><Divider section /></>)}
                    />

                    <CustomTextInput
                        name='username'
                        placeholder='Username'
                    />

                    <ErrorMessage
                        name='errorUsername'
                        render={() => <Label basic color='red' content={errors.errorUsername} />}
                    />

                    <CustomTextInput
                        type='password'
                        name='password'
                        placeholder='Password' />

                    <ErrorMessage
                        name='errorPassword'
                        render={() => <Label basic color='red' content={errors.errorPassword} />}
                    />

                    <Button
                        disabled={!isValid || !dirty || isSubmitting}
                        loading={isSubmitting}
                        positive
                        content='Login'
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

export default observer(LoginForm);