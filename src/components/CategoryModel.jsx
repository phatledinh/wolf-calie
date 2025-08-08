import { assets } from "../assets/image/assets";
import DropdownMenu from "./DropdownMenu";
const CategoryModel = () => {
    return (
        <div className="bg-[#E5E5E5] rounded-2xl">
            <h4 className="text-2xl font-medium text-black text-center pt-6">
                Bộ sưu tập
            </h4>
            <div className="grid grid-cols-12">
                <div className="col-span-12 md:col-span-6 lg:col-span-3 p-4">
                    <img
                        src={assets.menu_collection_1}
                        alt=""
                        className="rounded-2xl w-full h-auto"
                    />
                    <h5 className="font-light text-gray-800 mt-3 text-[16px]">
                        BST Peaceful
                    </h5>
                </div>
                <div className="col-span-12 md:col-span-6 lg:col-span-3 p-4">
                    <img
                        src={assets.menu_collection_1}
                        alt=""
                        className="rounded-2xl w-full h-auto"
                    />
                    <h5 className="font-light text-gray-800 mt-3 text-[16px]">
                        BST Peaceful
                    </h5>
                </div>
                <div className="col-span-12 md:col-span-6 lg:col-span-3 p-4">
                    <img
                        src={assets.menu_collection_1}
                        alt=""
                        className="rounded-2xl w-full h-auto"
                    />
                    <h5 className="font-light text-gray-800 mt-3 text-[16px]">
                        BST Peaceful
                    </h5>
                </div>
                <div className="col-span-12 md:col-span-6 lg:col-span-3 p-4">
                    <img
                        src={assets.menu_collection_1}
                        alt=""
                        className="rounded-2xl w-full h-auto"
                    />
                    <h5 className="font-light text-gray-800 mt-3 text-[16px]">
                        BST Peaceful
                    </h5>
                </div>
            </div>

            <div className="grid grid-cols-12 bg-white rounded-b-2xl pt-3">
                <div className="col-span-3 px-4">
                    <a href="#!">
                        <span>Nam</span>
                    </a>
                    <div className="scrollbar scrollbar-thumb-gray-400 mt-3 scrollbar-track-gray-100 scrollbar-thin max-h-[230px] overflow-y-auto">
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                    </div>
                </div>
                <div className="col-span-3 px-4">
                    <a href="#!">
                        <span>Nam</span>
                    </a>
                    <div className="scrollbar scrollbar-thumb-gray-400 scrollbar-track-gray-100 scrollbar-thin max-h-[230px] overflow-y-auto">
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                    </div>
                </div>
                <div className="col-span-3 px-4">
                    <a href="#!">
                        <span>Nam</span>
                    </a>
                    <div className="scrollbar scrollbar-thumb-gray-400 scrollbar-track-gray-100 scrollbar-thin max-h-[230px] overflow-y-auto">
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                    </div>
                </div>
                <div className="col-span-3 px-4">
                    <a href="#!">
                        <span>Nam</span>
                    </a>
                    <div className="scrollbar scrollbar-thumb-gray-400 scrollbar-track-gray-100 scrollbar-thin max-h-[230px] overflow-y-auto">
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                        <DropdownMenu
                            icon={"menu_icon_1"}
                            title={"Áo khoác"}
                            list_item={[
                                "Áo chống nắng",
                                "Áo vest",
                                "Áo phao",
                                "Áo gió",
                            ]}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CategoryModel;
