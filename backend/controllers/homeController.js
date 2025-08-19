const Product = require("../models/Product.js");
const Category = require("../models/Category.js");
const {
    getKidProductService,
    getCollectionService,
} = require("../services/productService.js");

exports.getHomeData = async (req, res) => {
    try {
        const flashSaleProducts = await Product.find()
            .limit(10)
            .populate("variants.colorId", "name hex");
        const collections = await getCollectionService();
        const popularItems = await Product.find()
            .skip(8)
            .limit(10)
            .populate("variants.colorId", "name hex");
        const newArrivals = await Product.find()
            .sort({ createdAt: 1 })
            .skip(3)
            .limit(6)
            .populate("variants.colorId", "name hex");
        const kidsItems = await getKidProductService();

        res.json({
            flashSaleProducts,
            collections,
            popularItems,
            newArrivals,
            kidsItems,
        });
    } catch (error) {
        res.status(500).json({ message: "Error fetching data", error });
    }
};
