import React, { useState } from "react";
import Banner from "../components/Banner";
import PromoMarquee from "../components/Marquee";
import CountdownTimer from "../components/CountdownTimer";
import { ChevronRight } from "lucide-react";
import CardProduct from "../components/CardProduct";
import ScrollbarSlide from "../components/ScrollbarSlide";
import Marquee from "react-fast-marquee";
import Button from "../components/Button";

import { assets } from "../assets/image/assets";
import SessionProduct from "../components/SessionProduct";
const Home = () => {
    const [active, setActive] = useState(0);

    return (
        <div>
            <Banner />
            <PromoMarquee />
            <div>
                <div className="container mx-auto px-14 py-8 bg-orange-300">
                    <div className="flex flex-col md:flex-row gap-8 items-center pb-4 justify-between md:px-30 lg:px-12">
                        <img src={assets.FlashSale} alt="" />
                        <CountdownTimer />
                        <button className="flex items-center bg-white text-red-700 text-2xl font-medium px-4 py-2 rounded-full hover:bg-red-700 hover:text-white transition-colors duration-300 max-lg:hidden">
                            Xem tất cả <ChevronRight />
                        </button>
                    </div>
                    <div className="grid grid-cols-2 lg:grid-cols-5 mx-6 sm:mx-30 md:mx-30 lg:mx-12 bg-white rounded-2xl pe-2">
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                        <CardProduct />
                    </div>
                </div>
                <div className="container mx-auto px-14 ">
                    <div className="flex items-center justify-between mt-8 md:px-30 lg:px-12">
                        <h4 className="text-3xl text-gray-800 font-medium">
                            Bộ sưu tập
                        </h4>
                        <div className="flex border-b border-gray-300">
                            {[
                                "Polo Cool 2025",
                                "Peaceful",
                                "Sport Nhẹ tênh",
                            ].map((title, index) => (
                                <button
                                    key={index}
                                    onClick={() => setActive(index)}
                                    className={`px-4 py-2 -mb-px text-[16px] font-medium border-b-2 transition ${
                                        active === index
                                            ? "text-gray-500 border-gray-500"
                                            : "text-gray-800 border-transparent hover:text-gray-800 hover:border-gray-800"
                                    }`}
                                >
                                    {title}
                                </button>
                            ))}
                        </div>
                    </div>

                    <div className="mt-4 md:px-30 lg:px-12">
                        {active === 0 && (
                            <div className="grid grid-cols-12">
                                <div className="col-span-4">
                                    <img
                                        src={assets.polo}
                                        alt=""
                                        className="rounded-2xl w-full max-w-[420px] h-auto mx-auto mt-4 mb-4"
                                    />
                                </div>
                                <div className="col-span-8">
                                    <ScrollbarSlide item={3} total={4} />
                                </div>
                            </div>
                        )}
                        {active === 1 && (
                            <div>
                                <div className="grid grid-cols-12">
                                    <div className="col-span-4">
                                        <img
                                            src={assets.jean}
                                            alt=""
                                            className="rounded-2xl w-full max-w-[420px] h-auto mx-auto mt-4 mb-4"
                                        />
                                    </div>
                                    <div className="col-span-8">
                                        <ScrollbarSlide item={3} total={4} />
                                    </div>
                                </div>
                            </div>
                        )}
                        {active === 2 && (
                            <div>
                                <div className="grid grid-cols-12">
                                    <div className="col-span-4">
                                        <img
                                            src={assets.sport}
                                            alt=""
                                            className="rounded-2xl w-full max-w-[420px] h-auto mx-auto mt-4 mb-4"
                                        />
                                    </div>
                                    <div className="col-span-8">
                                        <ScrollbarSlide item={3} total={4} />
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>

                    <div className="grid grid-cols-12 mt-4 md:px-30 lg:px-12">
                        <div className="col-span-4">
                            <img
                                src={assets.banner_1}
                                alt=""
                                className="rounded-2xl w-full max-w-[420px] h-auto mx-auto mt-4 mb-4 hover:scale-105 transition-transform duration-300  "
                            />
                        </div>
                        <div className="col-span-4">
                            <img
                                src={assets.banner_2}
                                alt=""
                                className="rounded-2xl w-full max-w-[420px] h-auto mx-auto mt-4 mb-4 hover:scale-105 transition-transform duration-300"
                            />
                        </div>
                        <div className="col-span-4">
                            <img
                                src={assets.banner_3}
                                alt=""
                                className="rounded-2xl w-full max-w-[420px] h-auto mx-auto mt-4 mb-4 hover:scale-105 transition-transform duration-300"
                            />
                        </div>
                    </div>
                    <div className="mt-4 md:px-30 lg:px-12">
                        <h4 className="text-3xl font-medium text-gray-800 hover:text-red-800 transition-colors duration-300">
                            Được yêu thích nhất
                        </h4>
                        <SessionProduct
                            image={"banner_4"}
                            grid_total={10}
                            grid_banner={4}
                        />
                        <SessionProduct
                            image={""}
                            grid_total={10}
                            grid_item={5}
                        />
                        <Button />
                    </div>

                    <div className="flex items-center justify-between mt-8 md:px-30 lg:px-12">
                        <h4 className="text-3xl text-gray-800 font-medium">
                            Hàng mới về
                        </h4>
                        <div className="flex border-b border-gray-300">
                            {["Nam", "Nữ", "Trẻ em", "Giày dép"].map(
                                (title, index) => (
                                    <button
                                        key={index}
                                        onClick={() => setActive(index)}
                                        className={`px-4 py-2 -mb-px text-[16px] font-medium border-b-2 transition ${
                                            active === index
                                                ? "text-gray-500 border-gray-500"
                                                : "text-gray-800 border-transparent hover:text-gray-800 hover:border-gray-800"
                                        }`}
                                    >
                                        {title}
                                    </button>
                                )
                            )}
                        </div>
                    </div>
                    <div className="mt-4 md:px-30 lg:px-12">
                        {active === 0 && <ScrollbarSlide item={5} total={6} />}
                        {active === 1 && <ScrollbarSlide item={5} total={6} />}
                        {active === 2 && <ScrollbarSlide item={5} total={6} />}
                        {active === 3 && <ScrollbarSlide item={5} total={6} />}
                    </div>
                    <Button />
                    <div className="mt-4 md:px-30 lg:px-12">
                        <h4 className="text-3xl font-medium text-gray-800 hover:text-red-800 transition-colors duration-300">
                            Dành cho bé
                        </h4>
                        <SessionProduct
                            image={"banner_5"}
                            grid_total={9}
                            grid_banner={3}
                        />
                        <SessionProduct
                            image={"banner_6"}
                            grid_total={9}
                            grid_banner={3}
                        />
                        <Button />
                    </div>

                    <div className="py-8 md:px-30 lg:px-12">
                        <h4 className="text-3xl text-gray-800 font-medium text-center pt-5">
                            Lookbook
                        </h4>
                        <div className="grid grid-cols-10 gap-2">
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_1}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_2}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_3}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_4}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_5}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_6}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_7}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_8}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_9}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                            <div className="col-span-2">
                                <img
                                    src={assets.lookbook_10}
                                    alt=""
                                    className="rounded-2xl w-full h-auto mx-auto mt-4 hover:scale-105 transition-transform duration-300"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Home;
