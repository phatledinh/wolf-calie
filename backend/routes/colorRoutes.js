const express = require("express");
const Color = require("../models/Color");
const router = express.Router();

router.post("/", async (req, res) => {
    try {
        const { name, hex } = req.body;
        const color = new Color({ name, hex });

        const createdColor = await color.save();
        res.status(201).json(createdColor);
    } catch (error) {
        console.log(error);
        res.status(500).send("Server Error");
    }
});
module.exports = router;
