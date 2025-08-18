const express = require("express");
const cors = require("cors");
const dotenv = require("dotenv");
const path = require("path");

const connectDB = require("./config/db");
const upload = require("./config/upload");
const userRoutes = require("./routes/userRoutes");
const categoryRoutes = require("./routes/categoryRoutes");
const productRoutes = require("./routes/productRoutes");
const colorRoutes = require("./routes/colorRoutes");
const homeRoutes = require("./routes/homeRoutes");

const app = express();
app.use(express.json());
app.use(cors());

dotenv.config();

const PORT = process.env.PORT || 3000;

connectDB();

app.get("/", (req, res) => {
    res.send("Hello nodejs");
});

app.use("/api/users", userRoutes);

app.use("/api/category", categoryRoutes);

app.use("/api/products", productRoutes);

app.use("/images", express.static(path.join(__dirname, "upload/images")));

app.use("/color", colorRoutes);

app.use("/api/pages", homeRoutes);

app.post("/upload", upload.single("product"), (req, res) => {
    res.json({
        success: 1,
        image_url: `http://localhost:${process.env.PORT}/images/${req.file.filename}`,
    });
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
