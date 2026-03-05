import React from "react";
import { usePlaidLink } from "react-plaid-link";
import { finalizeLink } from "../../api/plaidApi";

/**
 * Flow:
 * 1) Plaid Link opens and user authenticates bank
 * 2) Plaid returns public_token (short-lived)
 * 3) We POST public_token to backend
 * 4) Backend exchanges for access_token and stores encrypted
 */
export default function ConnectBankButton({ linkToken }) {
    const onSuccess = async (public_token, metadata) => {
        // metadata contains institution + accounts info
        const firstAccount = metadata.accounts?.[0];

        await finalizeLink({
            publicToken: public_token,
            institutionName: metadata.institution?.name,
            plaidAccountId: firstAccount?.id,
            accountName: firstAccount?.name,
            mask: firstAccount?.mask,
            accountType: firstAccount?.type,
            accountSubtype: firstAccount?.subtype,
        });
        alert("Bank connected!");
    };

    const { open, ready } = usePlaidLink({
        token: linkToken, // created by backend in real implementation (not included here)
        onSuccess,
    });

    return (
        <button
            className="px-4 py-2 rounded-xl bg-black text-white hover:opacity-90 disabled:opacity-50"
            onClick={() => open()}
            disabled={!ready}
        >
            Connect Bank
        </button>
    );
}