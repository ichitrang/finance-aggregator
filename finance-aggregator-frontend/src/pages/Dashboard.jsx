import React from "react";
import ConnectBankButton from "../components/plaid/ConnectBankButton";
import TransactionFeed from "../components/transactions/TransactionFeed";
import SpendAnalysis from "../components/analytics/SpendAnalysis";
import { getCategorySpend, getTrends } from "../api/analyticsApi";

export default function Dashboard() {
    const [categorySpend, setCategorySpend] = React.useState([]);
    const [trends, setTrends] = React.useState(null);
    const [txns, setTxns] = React.useState([]);
    const linkToken = "REPLACE_WITH_BACKEND_CREATED_LINK_TOKEN";

    React.useEffect(() => {
        (async () => {
            const [cats, tr] = await Promise.all([getCategorySpend(), getTrends()]);
            setCategorySpend(cats);
            setTrends(tr);

            // Transaction feed can come from backend endpoint you add later.
            // For now, you can either create /api/transactions?from&to or reuse repository query.
            // setTxns(await fetchTransactions());
        })();
    }, []);

    return (
        <div className="min-h-screen bg-gray-50">
            <div className="max-w-6xl mx-auto p-6">
                <div className="flex items-center justify-between mb-6">
                    <div>
                        <h1 className="text-2xl font-bold">Personal Finance Aggregator</h1>
                        <p className="text-gray-600">Connect accounts, sync transactions, analyze spending.</p>
                    </div>
                    <ConnectBankButton linkToken={linkToken} />
                </div>

                {trends && (
                    <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
                        <Stat title="Spending" value={trends.totalSpending} />
                        <Stat title="Income" value={trends.totalIncome} />
                        <Stat title="Net" value={trends.net} />
                        <Stat title="Avg Daily Spend" value={trends.avgDailySpending} />
                    </div>
                )}

                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <SpendAnalysis categorySpend={categorySpend} />
                    <TransactionFeed txns={txns} />
                </div>
            </div>
        </div>
    );
}

function Stat({ title, value }) {
    return (
        <div className="bg-white rounded-2xl shadow p-4">
            <div className="text-sm text-gray-600">{title}</div>
            <div className="text-xl font-semibold mt-1">{Number(value || 0).toFixed(2)}</div>
        </div>
    );
}