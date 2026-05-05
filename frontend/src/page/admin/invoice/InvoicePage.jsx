import { useEffect, useMemo, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import Button from "../../../component/Button";
import InputField from "../../../component/InputField";
import api from "../../../api";

export default function InvoicePage() {
  const { invoiceId } = useParams();
  const location = useLocation();
  const [invoice, setInvoice] = useState(location.state?.invoice ?? null);
  const [phone, setPhone] = useState("");
  const [member, setMember] = useState(null);
  const [memberError, setMemberError] = useState("");
  const [isCheckingMember, setIsCheckingMember] = useState(false);

  useEffect(() => {
    if (invoice || !invoiceId || invoiceId === "created") return;

    const fetchInvoice = async () => {
      try {
        const response = await api.get(`/invoices/${invoiceId}`);
        setInvoice(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchInvoice();
  }, [invoice, invoiceId]);

  const invoiceItems = useMemo(() => {
    return invoice?.orderItems  ?? [];
  }, [invoice]);

  const subtotal = invoice?.payBeforeDiscount ?? 0;
  const discount = invoice?.discountAmount ?? 0;
  const total = invoice?.totalPay ?? 0;

  const handleUpdateMember = async () => {
    if (!phone) {
      setMemberError("Enter member phone number");
      return;
    }

    const currentInvoiceId = invoice?.invoiceId ?? invoiceId;
    if (!currentInvoiceId || currentInvoiceId === "created") {
      setMemberError("Invoice not ready");
      return;
    }

    setIsCheckingMember(true);
    setMemberError("");

    try {
      const response = await api.put(`/invoices/addMember/${currentInvoiceId}`, null, {
        params: { memberPhone: phone },
      });
      const updatedMember = response.data;

      setInvoice((currentInvoice) => ({
        ...currentInvoice,
        invoiceId: updatedMember.invoiceId ?? currentInvoice?.invoiceId ?? currentInvoiceId,
        discountAmount: updatedMember.discountAmount ?? currentInvoice?.discountAmount,
        totalPay: updatedMember.totalPay ?? currentInvoice?.totalPay,
      }));
      setMember(updatedMember);
    } catch (error) {
      setMemberError(error.response?.data?.message || "Unable to update membership");
    } finally {
      setIsCheckingMember(false);
    }
  };

  return (
    <>
      <div className="flex justify-between items-start mb-6">
        <div>
          <h1 className="text-3xl font-bold text-main-navy">Invoice</h1>
          <p className="text-gray-500">Review invoice details and check membership.</p>
        </div>
        <span className="bg-second-cream rounded-2xl px-3 py-1 text-sm font-semibold text-main-navy">
          #{invoice?.invoiceNumber ?? invoice?.invoiceId ?? invoiceId}
        </span>
      </div>

      <div className="grid lg:grid-cols-3 gap-5">
        <div className="lg:col-span-2 bg-white rounded-xl border border-gray-300 p-6">
          <div className="grid sm:grid-cols-2 gap-4 mb-6">
            <InfoBlock label="Status" value={invoice?.invoiceStatus ?? invoice?.status ?? "Created"} />
            <InfoBlock label="Created" value={formatDate(invoice?.createdAt ?? invoice?.invoiceTime)} />
          </div>

          <div className="border border-gray-200 rounded-xl overflow-hidden">
            <div className="grid grid-cols-12 bg-input-bg px-4 py-3 text-sm font-bold text-gray-500">
              <div className="col-span-6">Item</div>
              <div className="col-span-2 text-center">Qty</div>
              <div className="col-span-2 text-right">Price</div>
              <div className="col-span-2 text-right">Total</div>
            </div>

            {invoiceItems.length === 0 ? (
              <div className="px-4 py-6 text-gray-500">No invoice items to show.</div>
            ) : (
              invoiceItems.map((item, index) => {
                const quantity = item.quantity ?? 1;
                const price = item.foodPrice ?? item.price ?? item.unitPrice ?? 0;
                return (
                  <div key={item.invoiceItemId ?? item.orderItemId ?? item.foodId ?? index} className="grid grid-cols-12 px-4 py-3 border-t border-gray-200">
                    <div className="col-span-6 font-semibold text-main-navy">{item.foodName ?? item.itemName ?? "Item"}</div>
                    <div className="col-span-2 text-center">{quantity}</div>
                    <div className="col-span-2 text-right">${Number(price).toFixed(2)}</div>
                    <div className="col-span-2 text-right">${Number(price * quantity).toFixed(2)}</div>
                  </div>
                );
              })
            )}
          </div>

          <div className="mt-6 flex justify-end">
            <div className="w-full sm:w-72 space-y-2">
              <PriceRow label="Subtotal" value={subtotal} />
              <PriceRow label="Discount" value={discount} />
              <div className="border-t border-gray-300 pt-2">
                <PriceRow label="Total" value={total} strong />
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white rounded-xl border border-gray-300 p-6 h-fit">
          <h5 className="text-xl font-bold text-main-navy mb-4">Membership</h5>
          <InputField
            label="Member Phone Number"
            inputType="text"
            placeholder="Enter phone number"
            value={phone}
            setValue={setPhone}
            error={memberError}
          />
          <Button variant="navy" width="w-full" onClick={handleUpdateMember} disabled={isCheckingMember}>
            {isCheckingMember ? "Updating..." : member ? "Change Membership" : "Apply Membership"}
          </Button>

          {member ? (
            <div className="mt-5 bg-input-bg border border-gray-200 rounded-xl p-4">
              <p className="font-bold text-main-navy">
                {`${member.memberFirstName ?? ""} ${member.memberLastName ?? ""}`.trim() || "Member applied"}
              </p>
              <p className="text-sm text-gray-500">Phone: {phone}</p>
              <p className="text-sm text-gray-500">Rank: {member.membershipRank ?? "-"}</p>
              <p className="text-sm text-gray-500">Discount: ${Number(member.discountAmount ?? 0).toFixed(2)}</p>
              <p className="text-sm text-gray-500">New Total: ${Number(member.totalPay ?? 0).toFixed(2)}</p>
            </div>
          ) : ""}
        </div>
      </div>
    </>
  );
}

function InfoBlock({ label, value }) {
  return (
    <div className="bg-input-bg rounded-xl border border-gray-200 p-4">
      <p className="text-sm text-gray-500">{label}</p>
      <p className="font-bold text-main-navy">{value}</p>
    </div>
  );
}

function PriceRow({ label, value, strong }) {
  return (
    <div className={`flex justify-between ${strong ? "font-bold text-lg text-main-navy" : "text-gray-600"}`}>
      <span>{label}</span>
      <span>${Number(value ?? 0).toFixed(2)}</span>
    </div>
  );
}

function formatDate(value) {
  if (!value) return "-";
  return new Date(value).toLocaleString();
}
