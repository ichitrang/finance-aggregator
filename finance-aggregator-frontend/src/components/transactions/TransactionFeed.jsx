import React, { useMemo } from "react";
import TxnFilters from "./TxnFilters";

export default function TransactionFeed({ txns }) {
    const [filters, setFilters] = React.useState({ category: "", from: "", to: "" });

    const categories = useMemo(() => {
        const s = new Set(txns.map((t) => t.category || "Uncategorized"));
        return Array.from(s).sort();
    }, [txns]);

    const filtered = useMemo(() => {
        return txns.filter((t) => {
            const cat = t.category || "Uncategorized";
            if (filters.category && cat !== filters.category) return false;
            if (filters.from && t.postedDate < filters.from) return false;
            if (filters.to && t.postedDate > filters.to) return false;
            return true;
        });
    }, [txns, filters]);

    return (
        <div className="bg-white rounded-2xl shadow p-5">
            <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-semibold">Transactions</h2>
            </div>

            <TxnFilters filters={filters} setFilters={setFilters} categories={categories} />

            <div className="mt-4 overflow-auto">
                <table className="min-w-full text-sm">
                    <thead className="text-left text-gray-600">
                    <tr>
                        <th className="py-2">Date</th>
                        <th className="py-2">Merchant</th>
                        <th className="py-2">Category</th>
                        <th className="py-2 text-right">Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filtered.map((t) => (
                        <tr key={t.id} className="border-t">
                            <td className="py-2">{t.postedDate}</td>
                            <td className="py-2">{t.merchantName || "-"}</td>
                            <td className="py-2">{t.category || "Uncategorized"}</td>
                            <td className="py-2 text-right font-medium">
                                {Number(t.amount).toFixed(2)}
                            </td>
                        </tr>
                    ))}
                    {filtered.length === 0 && (
                        <tr>
                            <td className="py-6 text-gray-500" colSpan={4}>No transactions.</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}