import React from "react";

export default function TxnFilters({ filters, setFilters, categories }) {
    return (
        <div className="flex flex-wrap gap-3 items-end">
            <div>
                <label className="text-sm text-gray-600">Category</label>
                <select
                    className="mt-1 border rounded-lg px-3 py-2"
                    value={filters.category}
                    onChange={(e) => setFilters((f) => ({ ...f, category: e.target.value }))}
                >
                    <option value="">All</option>
                    {categories.map((c) => (
                        <option key={c} value={c}>{c}</option>
                    ))}
                </select>
            </div>

            <div>
                <label className="text-sm text-gray-600">From</label>
                <input
                    type="date"
                    className="mt-1 border rounded-lg px-3 py-2"
                    value={filters.from}
                    onChange={(e) => setFilters((f) => ({ ...f, from: e.target.value }))}
                />
            </div>

            <div>
                <label className="text-sm text-gray-600">To</label>
                <input
                    type="date"
                    className="mt-1 border rounded-lg px-3 py-2"
                    value={filters.to}
                    onChange={(e) => setFilters((f) => ({ ...f, to: e.target.value }))}
                />
            </div>
        </div>
    );
}