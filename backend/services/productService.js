const Category = require("../models/Category");
const Product = require("../models/Product");

const getProductByCategoryService = async (slug) => {
    const category = await Category.findOne({ slug });
    if (!category) {
        throw new Error("Category not found!");
    }

    try {
        const product = await Product.find({ categoryId: category._id });
        return product;
    } catch (error) {
        console.log(">>>>>>>>>>> error: ", error);
        return null;
    }
};
const getProductBySlugService = async (slug) => {
    try {
        const product = await Product.findOne({ slug }).populate(
            "variants.colorId",
            "name hex"
        );
        return product;
    } catch (error) {
        console.log(">>>>>>>>>>> error: ", error);
        return null;
    }
};

const getRelatedProductService = async (id) => {
    try {
        const products = await Product.find({ categoryId: id })
            .limit(7)
            .populate("variants.colorId", "name hex");
        return products;
    } catch (error) {
        console.log(">>>>>>>>>>> error ", error);
        return null;
    }
};
const getKidProductService = async () => {
    try {
        const parentCategory = await Category.findOne({ name: "Trẻ em" });
        if (!parentCategory) {
            throw new Error("Parent category 'Trẻ em' not found!");
        }

        const subCategories = await Category.find({
            parentId: parentCategory._id,
        });

        const categoryIds = [
            parentCategory._id,
            ...subCategories.map((cat) => cat._id),
        ];

        const products = await Product.find({
            categoryId: { $in: categoryIds },
        })
            .limit(6)
            .populate("variants.colorId", "name hex");

        return products;
    } catch (error) {
        console.error("Error fetching kids products:", error);
        return null;
    }
};
const getCollectionService = async () => {
    const poloCategories = await Category.find({ name: "Áo polo" });
    const poloCategoryId = [...poloCategories.map((cat) => cat._id)];

    const poloProducts = await Product.find({
        categoryId: { $in: poloCategoryId },
    })
        .limit(4)
        .populate("variants.colorId", "name hex");

    const peacefulCategories = await Category.find({ name: "Áo sơ mi" });
    const peacefulCategoryId = [...peacefulCategories.map((cat) => cat._id)];

    const peacefulProducts = await Product.find({
        categoryId: { $in: peacefulCategoryId },
    })
        .limit(4)
        .populate("variants.colorId", "name hex");

    const sportCategories = await Category.find({ name: "Bộ thể thao" });
    const sportCategoryId = [...sportCategories.map((cat) => cat._id)];

    const sportProducts = await Product.find({
        categoryId: { $in: sportCategoryId },
    })
        .limit(4)
        .populate("variants.colorId", "name hex");

    return { poloProducts, peacefulProducts, sportProducts };
};
module.exports = {
    getProductByCategoryService,
    getProductBySlugService,
    getKidProductService,
    getCollectionService,
    getRelatedProductService,
};
