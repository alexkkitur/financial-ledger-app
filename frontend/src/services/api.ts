import type { FlaggedTransaction, TransactionResponse, TransferRequest } from "../types";

const base = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

async function request<T>(path: string, init: RequestInit = {}): Promise<T> {
  const token = localStorage.getItem("access_token");
  const response = await fetch(`${base}${path}`, {
    ...init,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...init.headers,
    },
  });
  const payload = await response.json().catch(() => null);
  if (!response.ok) {
    const message = payload && typeof payload.message === "string"
      ? payload.message
      : `Request failed (${response.status})`;
    throw new Error(message);
  }
  return payload as T;
}

export const transfer = (value: TransferRequest) =>
  request<TransactionResponse>("/api/transfers", {
    method: "POST",
    body: JSON.stringify(value),
  });

export async function flaggedTransactions(limit = 100): Promise<FlaggedTransaction[]> {
  const query = `{ flaggedTransactions(limit: ${limit}) { transactionHash occurredAt amountInCents flagReason } }`;
  const response = await request<{ data?: { flaggedTransactions: FlaggedTransaction[] }; errors?: { message: string }[] }>(
    "/graphql",
    { method: "POST", body: JSON.stringify({ query }) },
  );
  if (response.errors?.length) throw new Error(response.errors[0].message);
  if (!response.data) throw new Error("Compliance query returned no data");
  return response.data.flaggedTransactions;
}
