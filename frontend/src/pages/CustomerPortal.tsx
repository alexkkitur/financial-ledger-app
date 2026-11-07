import {FormEvent,useState} from "react";
import {transfer} from "../services/api";
export function CustomerPortal(){
  const [form,setForm]=useState({senderAccount:"",recipientAccount:"",amountInCents:""});
  const [message,setMessage]=useState("");
  async function submit(event:FormEvent){event.preventDefault();const cents=Number(form.amountInCents);if(!Number.isSafeInteger(cents)||cents<=0){setMessage("Enter a positive whole number of cents.");return}try{const tx=await transfer({...form,amountInCents:cents});setMessage(`Committed ${tx.transactionHash}${tx.flagged?" (AML review opened)":""}`)}catch(error){setMessage(error instanceof Error?error.message:"Transfer failed")}}
  return <main><h1>Customer portal</h1><form onSubmit={submit}><input required placeholder="Sender account" value={form.senderAccount} onChange={e=>setForm({...form,senderAccount:e.target.value})}/><input required placeholder="Recipient account" value={form.recipientAccount} onChange={e=>setForm({...form,recipientAccount:e.target.value})}/><input required min="1" step="1" type="number" placeholder="Amount in cents" value={form.amountInCents} onChange={e=>setForm({...form,amountInCents:e.target.value})}/><button type="submit">Transfer</button></form><p aria-live="polite">{message}</p></main>
}
