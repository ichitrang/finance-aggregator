import { api } from "./axios";

export async function getCategorySpend() {
    const { data } = await api.get("/analysis/categories");
    return data;
}

export async function getTrends() {
    const { data } = await api.get("/analysis/trends");
    return data;
}