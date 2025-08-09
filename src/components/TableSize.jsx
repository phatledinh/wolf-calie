import React from "react";
import { sizeCharts } from "../assets/size";

const TableSize = ({ title }) => {
    const { headers, rows } = sizeCharts[title] || {};

    return (
        <div className="overflow-x-auto">
            <table className="min-w-full border border-gray-200 rounded-lg overflow-hidden bg-white">
                <thead className="bg-gray-100">
                    <tr>
                        {headers.map((title, index) => (
                            <th
                                key={index}
                                className="px-6 py-3 text-left text-sm font-semibold text-gray-700 border-b"
                            >
                                {title}
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                    {rows.map((row, rowIndex) => (
                        <tr key={rowIndex} className="hover:bg-gray-50">
                            {row.map((cell, cellIndex) => (
                                <td
                                    key={cellIndex}
                                    className="px-6 py-3 text-sm text-gray-800"
                                >
                                    {cell}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default TableSize;
