import axios, { AxiosResponse } from 'axios';
import { Flight, FlightFormValues, FlightSearchFormValues } from '../models/flight';
import { LoginFormValues, RegisterFormValues, User } from '../models/user';
import { store } from '../store/store';

axios.defaults.baseURL = "http://localhost:8090/api";

const responseBody = <T>(response: AxiosResponse<T>) => response.data;

axios.interceptors.request.use(config => {
    const token = store.commonStore.token;

    if (token) config.headers.Authorization = `Bearer ${token}`;

    return config;
});

const requests =
{
    get: <T>(url: string) => axios.get<T>(url).then(responseBody),
    post: <T>(url: string, body: {}) => axios.post<T>(url, body).then(responseBody),
    del: <T>(url: string) => axios.delete<T>(url).then(responseBody)
}

const FlightAgent =
{
    getAllSave: () => requests.get<Flight[]>('/flight/save'),
    save: (flight: FlightFormValues) => requests.post<String>('/flight/save', flight),
    search: (flight: FlightSearchFormValues) => requests.post<Flight[]>('/flight/search', flight),
    searchSave: (flight: FlightSearchFormValues) => requests.post<Flight[]>('/flight/save/search', flight),
    deleteSave: (id: number) => requests.del<void>(`/flight/save/${id}`),
}

const UserAgent =
{
    current: () => requests.get<User>('/user'),
    login: (user: LoginFormValues) => requests.post<User>('/login', user),
    register: (user: RegisterFormValues) => requests.post<String>('/register', user)
}

const agent =
{
    FlightAgent,
    UserAgent
}

export default agent;