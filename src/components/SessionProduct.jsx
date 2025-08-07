import React from "react";
import { assets } from "../assets/image/assets";
import CardProduct from "./CardProduct";

const SessionProduct = (props) => {
    const { image, grid_total, grid_banner } = props;

    return (
        <div className={`grid grid-cols-${grid_total}`}>
            {image !== "" ? (
                <>
                    <div className={`col-span-${grid_banner}`}>
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
                    <div className="col-span-2">
                        <CardProduct />
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
                    <div className="col-span-2">
                        <CardProduct />
                    </div>
                </>
            )}
        </div>
    );
};

export default SessionProduct;
