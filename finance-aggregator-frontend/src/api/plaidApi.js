import { api } from "./axios";

export async function finalizeLink(payload) {
    const { data } = await api.post("/banks/link", payload);
    return data;
}