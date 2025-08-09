import React, { useState } from "react";
import { X, ChevronDown } from "lucide-react";
import TableSize from "./TableSize";
import SizeCalculator from "./SizeCalculator";
const SizeModel = ({ onClose }) => {
    const [selectedTitle, setSelectedTitle] = useState("Chung (Người lớn)");
    const [isOpen, setIsOpen] = useState(false);
    const [active, setActive] = useState(6);
    return (
        <div className="fixed inset-0 mx-auto mt-[30px] z-50 max-w-[800px] max-h-[675px] bg-gray-100 rounded-2xl overflow-y-auto">
            <div className="px-3 pt-5 pb-4 md:px-4 lg:px-3">
                <div className="flex items-center justify-between text-gray-500">
                    <h3 className="text-[20px] font-medium text-black">
                        Bảng size & Công cụ tính size
                    </h3>
                    <X onClick={onClose} className="hover:text-black" />
                </div>
            </div>
            <div className="bg-gray-600 w-full h-[1px]"></div>
            <div className="px-3 pt-5 pb-4 md:px-4 lg:px-3">
                <h4 className="text-[16px] font-medium text-black">
                    Chọn loại sản phẩm:
                </h4>
                <div
                    className="relative flex items-center justify-between w-full bg-white border-[1px]  rounded-[5px] h-[40px] hover:border-blue-500 transition-all duration-300 ease-in-out text-[16px]"
                    onClick={() => setIsOpen(!isOpen)}
                >
                    <span className="ps-2">{selectedTitle}</span>
                    <ChevronDown className="pe-2" />
                </div>
                <div
                    className={`absolute top-38 bg-white w-full px-10 md:px-4 lg:px-3 ${
                        isOpen ? "block" : "hidden"
                    }`}
                >
                    {[
                        "Áo nam",
                        "Quần nam",
                        "Áo nữ",
                        "Quần nữ",
                        "Áo trẻ em",
                        "Quần trẻ em",
                        "Chung (Người lớn)",
                    ].map((title, index) => (
                        <div
                            key={index}
                            onClick={() => {
                                setSelectedTitle(title),
                                    setIsOpen(!isOpen),
                                    setActive(index);
                            }}
                            className={`px-3 py-1 ${
                                selectedTitle === title ? "bg-blue-600" : ""
                            }`}
                        >
                            {title}
                        </div>
                    ))}
                </div>
                <h5 className="font-medium text-[24px] mt-4">
                    Bảng Size Tham Khảo ({selectedTitle})
                    {active === 0 && <TableSize title={selectedTitle} />}
                    {active === 1 && <TableSize title={selectedTitle} />}
                    {active === 2 && <TableSize title={selectedTitle} />}
                    {active === 3 && <TableSize title={selectedTitle} />}
                    {active === 4 && <TableSize title={selectedTitle} />}
                    {active === 5 && <TableSize title={selectedTitle} />}
                    {active === 6 && <TableSize title={selectedTitle} />}
                </h5>
                <h5 className="font-medium text-[24px] mt-4">
                    Tính Size Của Bạn ({selectedTitle})
                    {active === 0 && <SizeCalculator title={selectedTitle} />}
                    {active === 1 && <SizeCalculator title={selectedTitle} />}
                    {active === 2 && <SizeCalculator title={selectedTitle} />}
                    {active === 3 && <SizeCalculator title={selectedTitle} />}
                    {active === 4 && <SizeCalculator title={selectedTitle} />}
                    {active === 5 && <SizeCalculator title={selectedTitle} />}
                    {active === 6 && <SizeCalculator title={selectedTitle} />}
                </h5>
            </div>
        </div>
    );
};

export default SizeModel;
