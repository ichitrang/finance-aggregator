import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Shell from "./components/layout/Shell";

function Dashboard() {
    return <div className="p-6">Dashboard coming soon...</div>;
}

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />

                <Route element={<Shell />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}