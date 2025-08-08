import React, { useState } from "react";
import { assets } from "../assets/image/assets";
import { ChevronDown } from "lucide-react";

const DropdownMenu = (props) => {
    const [isOpen, setIsOpen] = useState(false);
    const { icon, title, list_item } = props;
    return (
        <div className="pb-2">
            <div className="flex items-center justify-between mt-2">
                <a href="#!" className="flex items-center space-x-2">
                    <img
                        src={assets[icon]}
                        alt=""
                        className="max-w-[40px] max-h-[40px] rounded-full"
                    />
                    <span className="text-[16px] hover:text-red-500">
                        {title}
                    </span>
                </a>
                <ChevronDown
                    onClick={() => setIsOpen(!isOpen)}
                    className={`cursor-pointer ${
                        isOpen ? "rotate-180" : ""
                    } transition-transform duration-200 ease-linear`}
                />
            </div>
            <div
                className={`flex flex-col space-y-2 ml-12 mt-3 transition-transform duration-200 ease-linear ${
                    isOpen ? "block" : "hidden"
                }`}
            >
                <a href="#!">
                    <span className="text-[16px] hover:text-red-500">
                        {list_item[0]}
                    </span>
                </a>
                <a href="#!">
                    <span className="text-[16px] hover:text-red-500">
                        {list_item[1]}
                    </span>
                </a>
                <a href="#!">
                    <span className="text-[16px] hover:text-red-500">
                        {list_item[2]}
                    </span>
                </a>
                <a href="#!">
                    <span className="text-[16px] hover:text-red-500">
                        {list_item[3]}
                    </span>
                </a>
            </div>
        </div>
    );
};

export default DropdownMenu;
