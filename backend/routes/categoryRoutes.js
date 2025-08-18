const express = require("express");
const Category = require("../models/Category");
const { getCategoryTree } = require("../controllers/CategoryController");
const router = express.Router();

router.post("/", async (req, res) => {
    try {
        const { name, slug, description, parentId, image } = req.body;

        const category = new Category({
            name,
            slug,
            description,
            parentId,
            image,
        });

        const createdCategory = await category.save();
        res.status(201).json(createdCategory);
    } catch (error) {
        console.error(error);
        res.status(500).send("Server Error");
    }
});

router.get("/", getCategoryTree);

module.exports = router;
