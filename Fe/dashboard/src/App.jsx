import React, {lazy, Suspense} from "react";
import {Routes, Route, Navigate} from "react-router-dom";

// home pages  & dashboard
//import Dashboard from "./pages/dashboard";
const Dashboard = lazy(() => import("./pages/dashboard"));

const Login = lazy(() => import("./pages/auth/login"));

const UsersPage = lazy(() => import("./pages/app/user/user.jsx"));

import Layout from "./layout/Layout";
import AuthLayout from "./layout/AuthLayout";

function App() {    return (
        <main className="App  relative">
            <Routes>
                <Route path="/" element={<AuthLayout/>}>
                    <Route path="/" element={<Login/>}/>
                </Route>
                <Route path="/*" element={<Layout/>}>
                    <Route path="dashboard" element={<Dashboard/>}/>
                    <Route path="user" element={<UsersPage/>}/>
                </Route>
            </Routes>
        </main>
    );
}

export default App;
