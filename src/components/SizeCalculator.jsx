// SizeCalculator.jsx
import React, { useState } from "react";
import { sizeCharts } from "../assets/size";

const SizeCalculator = ({ title }) => {
    const chart = sizeCharts[title];
    const [form, setForm] = useState({});
    const [result, setResult] = useState("");

    if (!chart) return null;

    const inputHeaders = chart.headers.filter((h) => h !== "SIZE");

    const handleChange = (header, value) => {
        setForm((prev) => ({ ...prev, [header]: value }));
    };

    const handleCalculate = () => {
        let matchedSize = null;

        for (let row of chart.rows) {
            let ok = true;
            for (let i = 1; i < row.length; i++) {
                const range = row[i].toString().split("-");
                const inputValue = parseFloat(form[chart.headers[i]]);

                if (range.length === 2) {
                    const min = parseFloat(range[0]);
                    const max = parseFloat(range[1]);
                    if (
                        isNaN(inputValue) ||
                        inputValue < min ||
                        inputValue > max
                    ) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                matchedSize = row[0];
                break;
            }
        }

        setResult(
            matchedSize
                ? `Size phù hợp: ${matchedSize}`
                : "Không tìm thấy size phù hợp"
        );
    };

    return (
        <div className="mt-4 p-2 border rounded-lg bg-white">
            {inputHeaders.map((header) => (
                <div key={header} className="mb-2">
                    <label className="block mb-1 text-[16px]">{header}</label>
                    <input
                        type="number"
                        value={form[header] || ""}
                        onChange={(e) => handleChange(header, e.target.value)}
                        className="border p-2 rounded w-full text-[16px]"
                    />
                </div>
            ))}
            <button
                onClick={handleCalculate}
                className="bg-blue-500 text-white px-4 py-2 rounded"
            >
                Tính size
            </button>
            {result && <p className="mt-3 font-medium">{result}</p>}
        </div>
    );
};

export default SizeCalculator;
