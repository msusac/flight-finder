import { makeAutoObservable, runInAction } from 'mobx';
import agent from '../api/agent';
import { LoginFormValues, RegisterFormValues, User } from '../models/user';
import { store } from './store';
import { history } from '../..';
import { toast } from 'react-toastify';

export default class UserStore {
    user: User | null = null;

    constructor() {
        makeAutoObservable(this)
    }

    get isLoggedIn() {
        return !!this.user;
    }

    getUser = async () => {
        try {
            const user = await agent.UserAgent.current();
            
            runInAction(() => this.user = user);
            store.modalStore.closeModal();
        }
        catch (error) {
            history.push('/');
        }
    }

    login = async (login: LoginFormValues) => {
        try {
            const user = await agent.UserAgent.login(login);
            store.commonStore.setToken(user.jwtToken);
            runInAction(() => this.user = user);
            history.push('/')
            store.modalStore.closeModal();
        }
        catch (error) {
            throw error;
        }
    }

    register = async (register: RegisterFormValues) => {
        try {
            const user = await agent.UserAgent.register(register);
            history.push('/');
            store.modalStore.closeModal();
            toast.success('Registration success! You can now login!');
        }
        catch (error) {
            throw error;
        }
    }

    logout = () => {
        store.commonStore.setToken(null);
        window.localStorage.removeItem('jwt');
        this.user = null;
        history.push('/');
        toast.success('You have successfully signed out!');
    }
}