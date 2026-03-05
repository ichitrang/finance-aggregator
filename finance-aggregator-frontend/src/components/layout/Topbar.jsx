import { useNavigate } from "react-router-dom";

export default function Topbar() {

    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem("token");
        navigate("/");
    };

    return (

        <header className="bg-white shadow">

            <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between">

                <h1 className="font-bold text-lg">
                    Finance Aggregator
                </h1>

                <button
                    onClick={logout}
                    className="text-sm bg-red-500 text-white px-4 py-2 rounded"
                >
                    Logout
                </button>

            </div>

        </header>

    );

}