import img_product from "../assets/image/product1.webp";
import { Heart, Eye, LayoutGrid } from "lucide-react";
const CardProduct = () => {
    return (
        <div className="py-4 ps-2 group">
            <div className="relative ">
                <img
                    src={img_product}
                    alt=""
                    className="w-full max-w-[460px] h-auto rounded-2xl"
                />
                <div className="flex flex-col justify-start items-center absolute top-2 right-2 gap-2 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                    <div className="bg-white rounded-full p-2 shadow-lg hover:shadow-xl transition-shadow duration-300">
                        <Heart />
                    </div>
                    <div className="bg-white rounded-full p-2 shadow-lg hover:shadow-xl transition-shadow duration-300">
                        <Eye />
                    </div>
                </div>
                <div className="absolute bottom-2 left-1/2 transform -translate-x-1/2 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                    <div className="flex items-center gap-3 bg-white px-10 py-1">
                        <LayoutGrid />
                        <span className="text-[10px] lg:text-[16px] font-medium text-gray-600 whitespace-nowrap">
                            Tùy chọn
                        </span>
                    </div>
                </div>
            </div>
            <div className="flex justify-start items-center gap-2 mt-3">
                <div className="rounded-full border-gray-400 border-2 w-6 h-6 flex items-center justify-center">
                    <div className="bg-green-300 rounded-full w-3/4 h-3/4"></div>
                </div>
                <div className="rounded-full border-gray-400 border-2 w-6 h-6 flex items-center justify-center">
                    <div className="bg-yellow-200 rounded-full w-3/4 h-3/4"></div>
                </div>
                <div className="rounded-full border-gray-400 border-2 w-6 h-6 flex items-center justify-center">
                    <div className="bg-red-400 rounded-full w-3/4 h-3/4"></div>
                </div>
            </div>
            <h3 className="text-gray-600 text-[12px] lg:text-[14px] font-semibold mt-2 hover:text-green-700">
                Áo Phông Nữ Slimfit Thun Rib Coton Mềm
            </h3>
            <div className="flex justify-start gap-3 mt-4">
                <span className="text-red-500 text-[14px] lg:text-[16px] font-semibold">
                    199.000đ
                </span>
                <span className="text-gray-400 line-through text-[12px] lg:text-[14px]">
                    299.000đ
                </span>
            </div>
        </div>
    );
};

export default CardProduct;
