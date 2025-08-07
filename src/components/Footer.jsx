import { Phone, Mail, MapPin } from "lucide-react";
import { assets } from "../assets/image/assets";
const Footer = () => {
    return (
        <div className="bg-[#2E2E2E] text-white h-[500px]">
            <div className="container mx-auto px-14 py-8">
                <div className="grid grid-cols-12 py-6">
                    <div className="col-span-12 md:col-span-4">
                        <h4 className="text-2xl font-medium">Wolf Calie</h4>
                        <p className="text-[16px] mt-2 w-3/4">
                            Thương hiệu thời trang uy tín đưa sản phẩm áo thun,
                            polo, jeans,.. có chất liệu tốt, dịch vụ tốt đến tận
                            tay khách hàng.
                        </p>
                    </div>
                    <div className="col-span-12 md:col-span-2">
                        <div className="flex items-center justify-start gap-3">
                            <Phone />
                            <div className="flex flex-col">
                                <span>Đặt hàng</span>
                                <span className="text-[20px] hover:text-red-500">
                                    1800 6750
                                </span>
                            </div>
                        </div>
                        <div className="flex items-center justify-start gap-3">
                            <Phone />
                            <div className="flex flex-col">
                                <span>Góp ý, Khiếu nại</span>
                                <span className="text-[20px] hover:text-red-500">
                                    1800 6750
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="col-span-12 md:col-span-3">
                        <div className="flex items-center justify-start gap-3">
                            <Mail />
                            <div className="flex flex-col">
                                <span>Email:</span>
                                <span className="text-[20px] hover:text-red-500">
                                    support@sapo.vn
                                </span>
                            </div>
                        </div>
                        <div className="flex items-center justify-start gap-3">
                            <MapPin />
                            <div className="flex flex-col">
                                <span>
                                    70 Lữ Gia, Phường 15, Quận 11, Thành phố Hồ
                                    Chí Minh
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="col-span-12 md:col-span-3">
                        <div className="flex justify-start items-center mt-3">
                            <div className="rounded-2xl border-1 border-white p-2 hover:border-red-500">
                                <img
                                    src={assets.facebook}
                                    alt="facebook"
                                    className="w-[35px] h-[35px]"
                                />
                            </div>
                            <div className="rounded-2xl ml-2 border-1 border-white p-2 hover:border-red-500">
                                <img
                                    src={assets.instagram}
                                    alt="instagram"
                                    className="w-[35px] h-[35px]"
                                />
                            </div>
                            <div className="rounded-2xl ml-2 border-1 border-white p-2 hover:border-red-500">
                                <img
                                    src={assets.shopee}
                                    alt="shopee"
                                    className="w-[35px] h-[35px]"
                                />
                            </div>
                            <div className="rounded-2xl ml-2 border-1 border-white p-2 hover:border-red-500">
                                <img
                                    src={assets.lazada}
                                    alt="lazada"
                                    className="w-[35px] h-[35px]"
                                />
                            </div>
                            <div className="rounded-2xl ml-2 border-1 border-white p-2 hover:border-red-500">
                                <img
                                    src={assets.tiktok}
                                    alt="tiktok"
                                    className="w-[35px] h-[35px]"
                                />
                            </div>
                        </div>
                    </div>
                </div>
                <div className="bg-white w-full h-[1px]"></div>
                <div className="grid grid-cols-12 py-6">
                    <div className="col-span-12 md:col-span-3">
                        <h4 className="text-2xl font-medium">Về chúng tôi</h4>
                        <ul>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Giới thiệu
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Hệ thống cửa hàng
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Thông tin tuyển dụng
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Liên hệ với chúng tôi
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div className="col-span-12 md:col-span-3">
                        <h4 className="text-2xl font-medium">Chính sách</h4>
                        <ul>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Chính sách bảo mật
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Chính sách giao hàng
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Chính sách đổi trả
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Điều khoản và Điều kiện
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div className="col-span-12 md:col-span-3">
                        <h4 className="text-2xl font-medium">Thông tin khác</h4>
                        <ul>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Phương thức thanh toán
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Khách hàng thân thiết
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Hoá đơn điện tử
                                </a>
                            </li>
                            <li className="mt-2">
                                <a href="#!" className="hover:text-red-500">
                                    Hướng dẫn mua hàng
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div className="col-span-12 md:col-span-3">
                        <h4 className="text-2xl font-medium">
                            Hỗ trợ thanh toán
                        </h4>
                        <div className="grid grid-cols-2 lg:grid-cols-5 gap-1 mt-2">
                            <img
                                src={assets.payment_1}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_2}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_3}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_4}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_5}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_6}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_7}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_8}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_9}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                            <img
                                src={assets.payment_10}
                                alt=""
                                className="lg:w-[60px] lg:h[40px]"
                            />
                        </div>
                        <div className="flex items-center justify-start mt-4">
                            <img
                                src={assets.zalo}
                                alt=""
                                className="max-w-[80px] max-h-[80px]"
                            />
                            <div className="ml-4 group">
                                <p className="group-hover:text-red-500">
                                    Zalo Mini Apps
                                </p>
                                <p className="group-hover:text-red-500">
                                    Quét mã QR để mua hàng nhanh chóng
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Footer;
