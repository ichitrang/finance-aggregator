import React from "react";
import { PieChart, Pie, Tooltip, ResponsiveContainer, Cell } from "recharts";

/**
 * Recharts note:
 * If you don't specify colors, it uses defaults. You can later add a palette if you want.
 */
export default function SpendAnalysis({ categorySpend }) {
    const data = categorySpend.map((x) => ({ name: x.category, value: Number(x.total) }));

    return (
        <div className="bg-white rounded-2xl shadow p-5">
            <h2 className="text-lg font-semibold mb-2">Spend Analysis (Last 30 Days)</h2>

            <div className="h-72">
                <ResponsiveContainer width="100%" height="100%">
                    <PieChart>
                        <Pie data={data} dataKey="value" nameKey="name" outerRadius={100} label />
                        <Tooltip />
                    </PieChart>
                </ResponsiveContainer>
            </div>
        </div>
    );
}