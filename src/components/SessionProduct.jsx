import clsx from "clsx";
import { assets } from "../assets/image/assets";
import CardProduct from "./CardProduct";

const SessionProduct = (props) => {
    const { image, grid_total, grid_banner, grid_item } = props;

    return (
        <div
            className={clsx("grid", {
                "grid-cols-9": grid_total === 9,
                "grid-cols-10": grid_total === 10,
                "grid-cols-12": grid_total === 12,
            })}
        >
            {image !== "" ? (
                <>
                    <div
                        className={clsx({
                            "col-span-3": grid_banner === 3,
                            "col-span-4": grid_banner === 4,
                        })}
                    >
                        <img
                            src={assets[image]}
                            alt=""
                            className="rounded-2xl w-full h-auto mt-4"
                        />
                    </div>
                    <div className="col-span-2">
                        <CardProduct />
                    </div>
                    <div className="col-span-2">
                        <CardProduct />
                    </div>
                    <div className="col-span-2">
                        <CardProduct />
                    </div>
                </>
            ) : (
                <>
                    {Array.from({ length: grid_item }).map((_, index) => (
                        <div key={index} className="col-span-2">
                            <CardProduct />
                        </div>
                    ))}
                </>
            )}
        </div>
    );
};

export default SessionProduct;
