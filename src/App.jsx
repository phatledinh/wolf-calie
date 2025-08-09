import Header from "./components/Header";
import { Route, Routes, useLocation } from "react-router-dom";
import Home from "./pages/Home";
import Footer from "./components/Footer";
import DetailProduct from "./pages/DetailProduct";

const App = () => {
    const isAdminRoute = useLocation().pathname.startsWith("/admin");
    return (
        <>
            {!isAdminRoute && <Header />}
            <Routes>
                <Route path="/" element={<Home />}></Route>
                <Route
                    path="/detail-product"
                    element={<DetailProduct />}
                ></Route>
            </Routes>
            {!isAdminRoute && <Footer />}
        </>
    );
};

export default App;
