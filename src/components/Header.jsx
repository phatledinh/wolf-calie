import React, { useEffect, useState } from "react";
import {
    Search,
    AlignJustify,
    Store,
    User,
    Heart,
    ShoppingCart,
    X,
} from "lucide-react";
import logo from "../assets/image/logo-light.webp";
import { Link } from "react-router-dom";
import CategoryModel from "./CategoryModel";
import Button from "./Button";
import SearchModel from "./SearchModel";
const Header = () => {
    const [isOpen, setIsOpen] = useState(true);
    const [isOpenSearch, setIsOpenSearch] = useState(true);
    useEffect(() => {
        if (!isOpen || !isOpenSearch) {
            document.body.style.overflow = "hidden";
        } else {
            document.body.style.overflow = "";
        }
    }, [isOpen, isOpenSearch]);
    return (
        <div className="absolute top-0 left-0 z-50 w-full flex items-center justify-between px-6 py-5 md:px-16 lg:px-36">
            <div className="flex items-center justify-between space-x-4">
                <Search
                    className="relative cursor-pointer w-[34px] h-[34px]"
                    onClick={() => setIsOpenSearch(!isOpenSearch)}
                />
                <div
                    className={`absolute top-[15px] w-full max-h-[570px] px-6 left-1/2 -translate-x-1/2 ${
                        isOpenSearch ? " hidden" : "block"
                    }`}
                >
                    <SearchModel />
                    <button
                        onClick={() => setIsOpenSearch(!isOpenSearch)}
                        className="flex items-center gap-2 rounded-4xl py-2 px-8 bg-[#E5E5E5] text-gray-600 mx-auto block mt-4 hover:text-white hover:bg-green-600 transition-colors duration-300"
                    >
                        <X />
                        Đóng
                    </button>
                </div>
                {isOpenSearch && (
                    <AlignJustify
                        className="relative cursor-pointer w-[34px] h-[34px]"
                        onClick={() => setIsOpen(!isOpen)}
                    />
                )}

                <div
                    className={`absolute top-[15px] w-full max-h-[570px] px-6 left-1/2 -translate-x-1/2 ${
                        isOpen ? " hidden" : "block"
                    }`}
                >
                    <CategoryModel />
                    <button
                        onClick={() => setIsOpen(!isOpen)}
                        className="flex items-center gap-2 rounded-4xl py-2 px-8 bg-[#E5E5E5] text-gray-600 mx-auto block mt-4 hover:text-white hover:bg-green-600 transition-colors duration-300"
                    >
                        <X />
                        Đóng
                    </button>
                </div>
                <Store className="w-[34px] h-[34px]" />
            </div>
            <div className="max-md:flex-1">
                <img src={logo} alt="Wolf Calie" className="w-40 h-full" />
            </div>
            <div className="flex items-center justify-between space-x-4">
                <User className="w-[34px] h-[34px]" />
                {isOpenSearch && isOpen && (
                    <>
                        <div className="relative cursor-pointer">
                            <Heart className="w-[34px] h-[34px]" />
                            <span className="absolute bottom-5 left-5 bg-green-500 text-white text-xs rounded-full px-2 py-1">
                                0
                            </span>
                        </div>
                        <div className="relative cursor-pointer">
                            <ShoppingCart className="w-[34px] h-[34px]" />
                            <span className="absolute bottom-5 left-5 bg-red-500 text-white text-xs rounded-full px-2 py-1">
                                0
                            </span>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default Header;
