import {
    Search,
    AlignJustify,
    Store,
    User,
    Heart,
    ShoppingCart,
} from "lucide-react";
import logo from "../assets/image/logo-light.webp";
import { Link } from "react-router-dom";
const Header = () => {
    return (
        <div className="absolute top-0 left-0 z-50 w-full flex items-center justify-between px-6 py-5 md:px-16 lg:px-36">
            <div className="flex items-center justify-between space-x-4">
                <Search />
                <AlignJustify />
                <Store />
            </div>
            <div className="max-md:flex-1">
                <img src={logo} alt="Wolf Calie" className="w-40 h-full" />
            </div>
            <div className="flex items-center justify-between space-x-4">
                <User />
                <Heart />
                <ShoppingCart />
            </div>
        </div>
    );
};

export default Header;
