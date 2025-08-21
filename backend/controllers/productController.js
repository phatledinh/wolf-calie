const {
    getProductByCategoryService,
    getProductBySlugService,
    getRelatedProductService,
} = require("../services/productService");

const getProductByCategory = async (req, res) => {
    try {
        const { slug } = req.params;
        const data = await getProductByCategoryService(slug);
        return res.status(200).json(data);
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
};

const getProductBySlug = async (req, res) => {
    try {
        const { slug } = req.params;
        const data = await getProductBySlugService(slug);
        return res.status(200).json(data);
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
};

const getRelatedProduct = async (req, res) => {
    try {
        const { id } = req.params;
        const data = await getRelatedProductService(id);

        return res.status(200).json(data);
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
};

module.exports = { getProductByCategory, getProductBySlug, getRelatedProduct };
