import { useField } from 'formik';
import React from 'react';
import { Form, Label, Select } from 'semantic-ui-react';

interface Props {
    placeholder: string;
    name: string;
    options: any;
    label?: string;
}

const CustomSelectInput = (props: Props) => {
    const [field, meta, helpers] = useField(props.name);

    return (
        <>
            <Label basic>{props.placeholder}</Label>

            <Form.Field
                error={meta.touched && !!meta.error}>

                <Select
                    clearable
                    options={props.options}
                    search
                    select
                    value={field.value || null}
                    onChange={(e, d) => helpers.setValue(d.value)}
                    onBlur={() => helpers.setTouched(true)}
                    placeholder={props.placeholder}
                />

                {meta.touched && meta.error ? (
                    <Label basic color='red'>{meta.error}</Label>
                ) : null}
                
            </Form.Field>
        </>
    )
}

export default CustomSelectInput;