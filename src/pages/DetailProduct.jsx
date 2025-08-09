import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Tooltip } from "react-tooltip";
import "react-tooltip/dist/react-tooltip.css";
import { assets } from "../assets/image/assets";
import { Check, Gift, Grid2x2, ShoppingCart } from "lucide-react";
import { colors } from "../assets/color";
import ScrollbarSlide from "../components/ScrollbarSlide";
import SizeModel from "../components/SizeModel";
const DetailProduct = () => {
    const [selectedColor, setSelectedColor] = useState("");
    const [selectedSize, setSelectedSize] = useState("");
    const [quantity, setQuantity] = useState(0);
    const [active, setActive] = useState(0);
    const [isOpen, setIsOpen] = useState(false);
    return (
        <div>
            <div className="container">
                <div className="bg-gray-300 w-full h-[1px]"></div>
                <ul className="py-[10px] px-[16px] flex items-center px-6 py-5 md:px-16 lg:px-36">
                    <li className="inline text-[18px] text-green-600 after:p-[8px] after:content-['/\00a0'] after:text-gray-500">
                        <Link to="/">Trang chủ</Link>
                    </li>
                    <li className="inline text-[18px] text-green-600 after:p-[8px] after:content-['/\00a0'] after:text-gray-500">
                        <Link to="/">Wolf Calie Infinite</Link>
                    </li>
                    <li className="inline text-[18px]">
                        <Link to="/">
                            Áo Phông Nữ Slimfit Thun Rib Coton Mềm
                        </Link>
                    </li>
                </ul>
                <div className="bg-gray-300 w-full h-[1px]"></div>
            </div>
            <div className="px-6 py-5 md:px-16 lg:px-36">
                <div className="grid grid-cols-12">
                    <div className="col-span-6">
                        <div className="grid grid-cols-12 gap-1">
                            <div className="col-span-6">
                                <img
                                    src={assets.product}
                                    alt="Product Detail"
                                    className="w-full h-auto rounded-2xl"
                                />
                            </div>
                            <div className="col-span-6">
                                <img
                                    src={assets.product}
                                    alt="Product Detail"
                                    className="w-full h-auto rounded-2xl"
                                />
                            </div>
                            <div className="col-span-6">
                                <img
                                    src={assets.product}
                                    alt="Product Detail"
                                    className="w-full h-auto rounded-2xl"
                                />
                            </div>
                            <div className="col-span-6">
                                <img
                                    src={assets.product}
                                    alt="Product Detail"
                                    className="w-full h-auto rounded-2xl"
                                />
                            </div>
                        </div>
                    </div>
                    <div className="col-span-6 ml-10">
                        <h1 className="text-[30px] font-medium mb-1 text-gray-700">
                            Áo Phông Nữ Slimfit Thun Rib Coton Mềm
                        </h1>
                        <span>
                            Mã:
                            <i className="text-gray-400 ml-2">Đang cập nhật</i>
                        </span>
                        <div>
                            <span>Thương hiệu:</span>
                            <span className="text-[#15B098] ml-2 hover:text-red-600">
                                Wolf Calie
                            </span>
                        </div>
                        <div>
                            <span>Tình trạng:</span>
                            <button className="bg-green-600 text-white rounded-2xl px-2 ml-2 text-[14px]">
                                Còn hàng
                            </button>
                        </div>
                        <div className="mt-4">
                            <span className="text-[30px] font-medium text-red-700">
                                199.000₫
                            </span>
                            <span className="text-[16px] text-gray-400 line-through ml-3">
                                299.000₫
                            </span>
                        </div>
                        <div>
                            <span className="text-gray-400">Tiết kiệm:</span>
                            <span className="text-[#15B098] ml-1">50.000₫</span>
                            <span className="text-gray-400 ml-1">
                                so với giá thị trường
                            </span>
                        </div>
                        <div className="relative">
                            <div className="bg-[#FFEAED] border border-red-500 rounded-2xl px-4 py-2 mt-8">
                                <ul>
                                    <li className="flex items-center gap-2 mt-4">
                                        <Check />
                                        <span>
                                            <strong>Giảm 10%</strong> cho đơn
                                            hàng từ 3 sản phẩm trở lên.
                                        </span>
                                    </li>
                                    <li className="flex items-center gap-2 mt-4">
                                        <Check />
                                        <span>
                                            <strong className="mr-1">
                                                Miễn phí giao hàng
                                            </strong>
                                            toàn quốc cho đơn hàng trên 500.000
                                            VNĐ.
                                        </span>
                                    </li>
                                    <li className="flex items-center gap-2 mt-4">
                                        <Check />
                                        <span>
                                            Tặng ngay
                                            <strong className="mx-1">
                                                voucher 50.000 VNĐ
                                            </strong>
                                            cho khách hàng mới.
                                        </span>
                                    </li>
                                </ul>
                            </div>
                            <div className="absolute top-[-17px] left-8 flex items-center gap-1 justify-center bg-[#F85A40] text-white rounded-[8px] px-4 py-2">
                                <Gift />
                                <span>Khuyến mãi đặc biệt</span>
                            </div>
                            <div className="mt-3">
                                <span>Màu sắc:</span>
                                <span className="text-gray-400 ml-2">
                                    {selectedColor || "Chọn màu sắc"}
                                </span>
                            </div>
                            <div className="mt-2">
                                {[
                                    "Trắng",
                                    "Than chì",
                                    "Đen",
                                    "Cam",
                                    "Xám",
                                    "Đỏ",
                                    "Oliu",
                                ].map((color, index) => (
                                    <>
                                        <button
                                            key={index}
                                            data-tooltip-id="color-tooltip"
                                            data-tooltip-content={`${color}`}
                                            onClick={() =>
                                                setSelectedColor(color)
                                            }
                                            className={`w-[60px] h-[40px] border-2 ${
                                                selectedColor === color
                                                    ? "border-red-700"
                                                    : "border-gary-300"
                                            } mr-2`}
                                            style={{
                                                backgroundColor: colors[color],
                                            }}
                                        />
                                        <Tooltip
                                            id="color-tooltip"
                                            style={{
                                                backgroundColor: "#00A98F",
                                                color: "#fff",
                                            }}
                                        />
                                    </>
                                ))}
                            </div>
                            <div className="mt-3 flex items-center justify-between">
                                <div>
                                    <span>Size:</span>
                                    <span className="text-gray-400 ml-2">
                                        {selectedSize || "Chọn size"}
                                    </span>
                                </div>
                                <div className="flex items-center gap-1 cursor-pointer text-blue-500 hover:text-red-600">
                                    <Grid2x2 />
                                    <span onClick={() => setIsOpen(true)}>
                                        Gợi ý tìm size
                                    </span>
                                </div>
                            </div>
                            <div className="mt-2">
                                {["S", "M", "L", "XL"].map((size, index) => (
                                    <>
                                        <button
                                            data-tooltip-id="size-tooltip"
                                            data-tooltip-content={`${size}`}
                                            key={index}
                                            onClick={() =>
                                                setSelectedSize(size)
                                            }
                                            className={`w-[60px] h-[40px] border-2 ${
                                                selectedSize === size
                                                    ? "border-red-700 bg-white"
                                                    : "border-gary-300 bg-gray-100"
                                            } mr-2`}
                                        >
                                            {size}
                                        </button>
                                        <Tooltip
                                            id="size-tooltip"
                                            style={{
                                                backgroundColor: "#00A98F",
                                                color: "#fff",
                                            }}
                                        />
                                    </>
                                ))}
                            </div>
                        </div>
                        <div className="mt-4">
                            <span>Số lượng:</span>
                            <button
                                className="rounded-l-2xl bg-blue-200 text-[#00A98F] px-4 py-2 text-center ml-3"
                                onClick={() =>
                                    setQuantity(quantity > 0 ? quantity - 1 : 0)
                                }
                            >
                                -
                            </button>
                            <input
                                type="text"
                                className="bg-[#F8FAFC] w-[80px] h-[40px] text-center ml-1"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                            />
                            <button
                                className="rounded-r-2xl bg-blue-200 text-[#00A98F] px-4 py-2 text-center"
                                onClick={() =>
                                    setQuantity(
                                        quantity < 10 ? quantity + 1 : quantity
                                    )
                                }
                            >
                                +
                            </button>
                        </div>
                        <div className="mt-4 flex justify-between items-stretch">
                            <button className="bg-[#00A98F] text-white rounded-2xl px-10 py-2 flex flex-col items-center justify-center">
                                <span className="font-medium text-[20px]">
                                    Mua ngay
                                </span>
                                <span className="text-[14px]">
                                    (Giao tận nơi hoặc nhận tại cửa hàng)
                                </span>
                            </button>

                            <button className="bg-red-600 text-white rounded-2xl px-6 flex items-center justify-center gap-2">
                                <ShoppingCart />
                                <span className="font-medium text-[18px]">
                                    Thêm vào giỏ
                                </span>
                            </button>
                        </div>
                    </div>
                </div>
                <h4 className="font-medium text-2xl mt-8">
                    WOLF CALIE cam kết
                </h4>
                <div className="grid grid-cols-12 gap-2 mt-3">
                    <div className="col-span-3">
                        <div className="flex items-center rounded-2xl border-1 border-gray-400 p-2">
                            <img
                                src={assets.commit_1}
                                alt=""
                                className="max-w-[64px] max-h-[64px] w-full h-auto bg-gray-100 rounded-2xl"
                            />
                            <span className="text-[16px] ml-2">
                                Cam kết sản phẩm đúng mô tả, chất liệu cao cấp.
                            </span>
                        </div>
                    </div>
                    <div className="col-span-3">
                        <div className="flex items-center rounded-2xl border-1 border-gray-400 p-2">
                            <img
                                src={assets.commit_2}
                                alt=""
                                className="max-w-[64px] max-h-[64px] w-full h-auto bg-gray-100 rounded-2xl"
                            />
                            <span className="text-[16px] ml-2">
                                Giao trong 3-5 ngày và freeship đơn từ 498k
                            </span>
                        </div>
                    </div>
                    <div className="col-span-3">
                        <div className="flex items-center rounded-2xl border-1 border-gray-400 p-2">
                            <img
                                src={assets.commit_3}
                                alt=""
                                className="max-w-[64px] max-h-[64px] w-full h-auto bg-gray-100 rounded-2xl"
                            />
                            <span className="text-[16px] ml-2">
                                Hỗ trợ đổi trả trong 7 ngày nếu sản phẩm lỗi.
                            </span>
                        </div>
                    </div>
                    <div className="col-span-3">
                        <div className="flex items-center rounded-2xl border-1 border-gray-400 p-2">
                            <img
                                src={assets.commit_4}
                                alt=""
                                className="max-w-[64px] max-h-[64px] w-full h-auto bg-gray-100 rounded-2xl"
                            />
                            <span className="text-[16px] ml-2">
                                Đội ngũ tư vấn tận tâm, giải đáp nhanh chóng
                            </span>
                        </div>
                    </div>
                </div>
            </div>
            <div className="px-6 py-5 md:px-16 lg:px-36">
                <div className="w-full border-b-1 flex items-center justify-center border-gray-300">
                    {[
                        "Thông tin sản phẩm",
                        "Bảo quản",
                        "Giao hàng & Đổi trả",
                    ].map((title, index) => (
                        <button
                            key={index}
                            onClick={() => setActive(index)}
                            className={`px-6 py-3 text-[18px] font-medium ${
                                active === index
                                    ? "border-b-4 border-[#00A98F] text-[#00A98F]"
                                    : "text-gray-500"
                            }`}
                        >
                            {title}
                        </button>
                    ))}
                </div>
                <div className="mt-6">
                    {active === 0 && (
                        <span className="text-[16px] text-gray-600">
                            Áo thun tôn dáng dành riêng cho nàng. Thiết kế cổ
                            tròn, dáng ôm tôn lên đường cong cơ thể giúp nàng
                            trông thon gọn và năng động hơn. Sản phẩm cho cảm
                            giác mặc siêu mềm mại, siêu co giãn, xứng đáng là
                            một item không thể thiếu trong tủ đồ hàng ngày cho
                            các chị em.
                        </span>
                    )}
                    {active === 1 && (
                        <ul className="list-disc pl-6 text-[16px] text-gray-600">
                            <li>Giặt bằng nước ở nhiệt độ 30℃</li>
                            <li>Phơi ở nơi nắng nhẹ, thoáng gió</li>
                            <li>Không giặt chung với sản phẩm khác màu</li>
                            <li>Tránh giặt với các chất giặt tẩy mạnh</li>
                            <li>Không phơi trực tiếp dưới ánh nắng mặt trời</li>
                            <li>
                                Lựa chọn chế độ giặt phù hợp theo như trên tag
                                sản phẩm
                            </li>
                        </ul>
                    )}
                    {active === 2 && (
                        <div className="text-gray-600">
                            <span className="text-[16px] block">
                                Áp dụng cho toàn bộ sản phẩm quần áo nguyên giá
                                của <strong>Wolf Calie</strong>
                            </span>
                            <span className="block text-[16px]">
                                <strong className="mr-1">
                                    Đối tượng khách hàng:
                                </strong>
                                Tất cả khách hàng sử dụng dịch vụ tại
                                <strong className="ml-1">Wolf Calie</strong>
                            </span>
                            <span className="block text-[16px]">
                                <strong>Thời gian đổi/ trả hàng:</strong>
                            </span>
                            <ul className="list-disc pl-6 text-[16px] py-2">
                                <li>
                                    <strong className="mr-1">Đổi hàng:</strong>
                                    Trong vòng
                                    <strong className="ml-1 mr-1">
                                        30 ngày
                                    </strong>
                                    kể từ ngày khách hàng nhận được sản phẩm.
                                </li>
                                <li>
                                    <strong className="ml-1">Trả hàng:</strong>
                                    Trong vòng
                                    <strong className="ml-1 mr-1">
                                        30 ngày
                                    </strong>
                                    kể từ ngày khách hàng nhận được sản phẩm.
                                </li>
                            </ul>
                            <span className="block text-[16px]">
                                <i>
                                    Lưu ý: Không áp dụng cho các sản phẩm giảm
                                    giá từ 30% trở lên và các sản phẩm mua trực
                                    tiếp tại hệ thống cửa hàng của Wolf Calie.
                                </i>
                            </span>
                            <span className="block text-[16px]">
                                <u className="mr-1">Ghi chú:</u> Thời hạn
                                đổi/trả hàng được tính từ ngày khách hàng nhận
                                hàng cho đến ngày khách hàng gửi hàng đổi/trả
                                cho đơn vị vận chuyển.
                            </span>
                        </div>
                    )}
                </div>
            </div>
            <div className="px-6 py-5 md:px-16 lg:px-36 mb-6">
                <h3 className="text-3xl font-medium">Cùng danh mục</h3>
                <ScrollbarSlide item={5} total={6} />
            </div>
            {isOpen && <SizeModel onClose={() => setIsOpen(false)} />}
        </div>
    );
};

export default DetailProduct;
