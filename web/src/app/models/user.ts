export interface User {
    username: string;
    jwtToken: string | null;
    airToken: string | null;
}

export interface LoginFormValues {
    username: string;
    password: string;
}

export interface RegisterFormValues {
    username: string;
    password: string;
    email: string;
}