// Import Swiper React components
import { Swiper, SwiperSlide } from "swiper/react";

// Import Swiper styles
import "swiper/css";
import "swiper/css/scrollbar";
import "swiper/css/navigation"; // 💡 Thêm style cho navigation (arrow)

// import required modules
import { Scrollbar, Navigation } from "swiper/modules"; // 💡 Thêm Navigation
import CardProduct from "./CardProduct";

const ScrollbarSlide = (props) => {
    const slides = Array.from({ length: props.total }, (_, index) => (
        <SwiperSlide key={index}>
            <CardProduct />
        </SwiperSlide>
    ));

    return (
        <Swiper
            slidesPerView={props.item}
            spaceBetween={20}
            scrollbar={{ draggable: true }}
            navigation={true}
            modules={[Scrollbar, Navigation]}
            className="mySwiper"
        >
            {slides}
        </Swiper>
    );
};

export default ScrollbarSlide;
