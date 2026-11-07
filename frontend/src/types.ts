export interface TransferRequest { senderAccount: string; recipientAccount: string; amountInCents: number }
export interface TransactionResponse { id: string; transactionHash: string; senderId: string; recipientId: string; amountInCents: number; occurredAt: string; flagged: boolean; flagReason?: string }
export interface FlaggedTransaction { transactionHash: string; occurredAt: string; amountInCents: number; flagReason?: string }
