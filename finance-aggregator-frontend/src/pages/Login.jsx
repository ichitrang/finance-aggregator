import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const login = async (e) => {

        e.preventDefault();

        try {

            const res = await axios.post(
                "http://localhost:8080/api/auth/login",
                { email, password }
            );

            localStorage.setItem("token", res.data.token);

            navigate("/dashboard");

        } catch (err) {

            alert("Login failed");

        }

    };

    return (

        <div className="flex items-center justify-center h-screen">

            <form
                onSubmit={login}
                className="bg-white p-8 shadow-md rounded w-96"
            >

                <h2 className="text-2xl font-bold mb-6 text-center">
                    Login
                </h2>

                <input
                    type="email"
                    placeholder="Email"
                    className="border w-full mb-4 p-2"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Password"
                    className="border w-full mb-4 p-2"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button
                    className="bg-blue-500 text-white w-full py-2 rounded"
                >
                    Login
                </button>

            </form>

        </div>

    );

}