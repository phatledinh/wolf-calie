const { getProductByCategoryService } = require("../services/productService");

const getProductByCategory = async (req, res) => {
    try {
        const { slug } = req.params;
        const data = await getProductByCategoryService(slug);
        return res.status(200).json(data);
    } catch (error) {
        console.log(">>>>>>>>>>>>>> error", error);
        return res.status(400).json({ message: error.message });
    }
};
module.exports = { getProductByCategory };
