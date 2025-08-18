const mongoose = require("mongoose");

const ColorSchema = new mongoose.Schema(
    {
        name: { type: String, required: true, trim: true, unique: true },
        hex: { type: String, required: true, match: /^#([0-9A-Fa-f]{6})$/ },
    },
    { timestamps: true }
);

module.exports = mongoose.model("Color", ColorSchema);
