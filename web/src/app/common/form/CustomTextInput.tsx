import { useField } from 'formik';
import React from 'react';
import { Form, Label } from 'semantic-ui-react';

interface Props {
    placeholder: string;
    name: string;
    type?: string;
    label?: string;
}

const CustomTextInput = (props: Props) => {
    const [field, meta] = useField(props.name);
    const { placeholder, ...clonedProps } = props;

    return (
        <>
            <Label basic>{props.placeholder}</Label>

            <Form.Field
                error={meta.touched && !!meta.error}>
                <label>{props.label}</label>
                <input
                    {...field}
                    {...clonedProps}
                />

                {meta.touched && meta.error ? (
                    <Label basic color='red'>{meta.error}</Label>
                ) : null}

            </Form.Field>
        </>
    )
}

export default CustomTextInput;