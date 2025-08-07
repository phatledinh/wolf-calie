import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import slider_1 from "../assets/image/slider_1.webp";
import slider_2 from "../assets/image/slider_2.webp";

const Banner = () => {
    const responsive = {
        desktop: {
            breakpoint: { max: 3000, min: 1024 },
            items: 1,
            slidesToSlide: 1,
        },
        tablet: {
            breakpoint: { max: 1024, min: 464 },
            items: 1,
            slidesToSlide: 1,
        },
        mobile: {
            breakpoint: { max: 464, min: 0 },
            items: 1,
            slidesToSlide: 1,
        },
    };
    return (
        <div className="relative">
            <Carousel
                swipeable={false}
                draggable={false}
                showDots={false}
                arrows={false}
                responsive={responsive}
                ssr={true}
                infinite={true}
                autoPlay={true}
                autoPlaySpeed={3000}
                keyBoardControl={true}
                customTransition="all .5"
                transitionDuration={500}
                containerClass="carousel-container"
                removeArrowOnDeviceType={["tablet", "mobile"]}
                dotListClass="custom-dot-list-style"
                itemClass="carousel-item-padding-40-px"
            >
                <div>
                    <img src={slider_1} alt="" />
                </div>
                <div>
                    <img src={slider_2} alt="" />
                </div>
            </Carousel>
        </div>
    );
};

export default Banner;
