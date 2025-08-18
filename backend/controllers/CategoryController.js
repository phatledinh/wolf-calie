const Category = require("../models/Category");

const buildCategoryTree = (categories, parentId = null, baseUrl) => {
    let categoryList = [];
    let filteredCategories;

    if (parentId == null) {
        filteredCategories = categories.filter((cat) => !cat.parentId);
    } else {
        filteredCategories = categories.filter(
            (cat) => cat.parentId?.toString() === parentId.toString()
        );
    }

    for (let cat of filteredCategories) {
        categoryList.push({
            _id: cat._id,
            name: cat.name,
            slug: cat.slug,
            description: cat.description,
            // Thêm baseUrl vào ảnh
            image: cat.image ? `${baseUrl}${cat.image}` : null,
            children: buildCategoryTree(categories, cat._id, baseUrl),
        });
    }

    return categoryList;
};

exports.getCategoryTree = async (req, res) => {
    try {
        const categories = await Category.find().lean();

        const baseUrl = `${req.protocol}://${req.get("host")}/images/`;

        const categoryTree = buildCategoryTree(categories, null, baseUrl);
        res.json(categoryTree);
    } catch (err) {
        console.error(err);
        res.status(500).json({ message: "Server error" });
    }
};
