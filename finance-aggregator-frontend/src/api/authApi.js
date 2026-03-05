import { api } from "./axios";

export async function login(email, password) {
    const { data } = await api.post("/auth/login", { email, password });
    localStorage.setItem("jwt", data.token);
    return data;
}

export async function register(email, password, fullName) {
    const { data } = await api.post("/auth/register", { email, password, fullName });
    localStorage.setItem("jwt", data.token);
    return data;
}