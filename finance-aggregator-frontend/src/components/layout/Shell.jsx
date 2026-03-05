import { Outlet } from "react-router-dom";
import Topbar from "./Topbar";

export default function Shell() {

    return (
        <div className="min-h-screen bg-gray-100">

            <Topbar />

            <main className="p-6">
                <Outlet />
            </main>

        </div>
    );

}