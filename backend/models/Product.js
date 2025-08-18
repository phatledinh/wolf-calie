const mongoose = require("mongoose");

const SizeSchema = new mongoose.Schema({
    name: { type: String, required: true },
    quantity: { type: Number, required: true, min: 0 },
});

const VariantSchema = new mongoose.Schema({
    colorId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Color",
        required: true,
    },
    images: [{ type: String, required: true }],
    sizes: [SizeSchema],
});

const ProductSchema = new mongoose.Schema(
    {
        name: { type: String, required: true, trim: true },
        slug: { type: String, required: true, unique: true, lowercase: true },
        thumbnail: { type: String, required: true },
        description: [{ type: String, trim: true }],
        price: { type: Number, required: true, min: 0 },
        sale_price: { type: Number, default: null },
        categoryId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: "Category",
            required: true,
        },
        brand: { type: String, required: true },
        variants: [VariantSchema],
        status: {
            type: String,
            enum: ["active", "inactive"],
            default: "active",
        },
    },
    { timestamps: true }
);

module.exports = mongoose.model("Product", ProductSchema);
