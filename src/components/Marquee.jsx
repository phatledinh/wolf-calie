import Marquee from "react-fast-marquee";

const PromoMarquee = () => {
    return (
        <div>
            <Marquee
                speed={50}
                gradient={false}
                autoFill={true}
                className="h-[70px] bg-red-700 text-white text-2xl font-semibold"
            >
                HURRY UP! &nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp; PRICE DOWN
                &nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;
            </Marquee>
        </div>
    );
};

export default PromoMarquee;
