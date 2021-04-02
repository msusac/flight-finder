import { useField } from 'formik';
import React from 'react';
import { Form, Label } from 'semantic-ui-react';
import DatePicker, { ReactDatePickerProps } from 'react-datepicker';

const CustomDateInput = (props: Partial<ReactDatePickerProps>) => {
    const [field, meta, helpers] = useField(props.name!);

    var date = new Date(field.value);

    date.setMinutes(date.getMinutes() + date.getTimezoneOffset());

    return (
        <>
            <Label basic>{props.title}</Label>

            <Form.Field
                error={meta.touched && !!meta.error}>

                <DatePicker
                    {...field}
                    {...props}
                    selected={(field.value && date) || null}
                    onChange={value => helpers.setValue(value)} />

                {meta.touched && meta.error ? (
                    <Label basic color='red'>{meta.error}</Label>
                ) : null}

            </Form.Field>
        </>
    )
}

export default CustomDateInput;