const express = require("express");
const Product = require("../models/Product");
const router = express.Router();
const {
    getProductByCategory,
    getProductBySlug,
    getRelatedProduct,
} = require("../controllers/productController");

router.post("/", async (req, res) => {
    try {
        const {
            name,
            slug,
            description,
            price,
            sale_price,
            thumbnail,
            categoryId,
            brand,
            variants,
            status,
        } = req.body;

        const product = new Product({
            name,
            slug,
            description,
            price,
            sale_price,
            thumbnail,
            categoryId,
            brand,
            variants,
            status,
        });

        const createdProduct = await product.save();
        res.status(201).json(createdProduct);
    } catch (error) {
        console.error(error);
        res.status(500).send("Server Error");
    }
});

router.get("/category/:slug", getProductByCategory);
router.get("/:slug", getProductBySlug);
router.get("/related-product/:id", getRelatedProduct);

module.exports = router;
