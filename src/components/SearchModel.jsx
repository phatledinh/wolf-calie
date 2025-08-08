import React from "react";
import { Search } from "lucide-react";
import SessionProduct from "./SessionProduct";
const SearchModel = () => {
    return (
        <div className="bg-[#E5E5E5] rounded-2xl pb-4">
            <h4 className="text-2xl font-medium text-black text-center pt-3">
                Tìm kiếm
            </h4>
            <div className="flex items-center justify-center space-x-2 mt-4 px-4 relative">
                <input
                    type="text"
                    placeholder="Tìm kiếm trong Wolf Calie"
                    className="bg-white rounded-2xl max-w-[600px] w-full px-4 py-2 focus:outline-none"
                />
                <Search className="absolute right-115 text-green-600" />
            </div>
            <div className="bg-white rounded-2xl mt-4 mx-4 pe-3">
                <h4 className="text-2xl font-medium text-black text-center pt-2">
                    Sản phẩm xu hướng
                </h4>
                <SessionProduct image={""} grid_total={12} grid_item={6} />
            </div>
        </div>
    );
};

export default SearchModel;
