import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../../component/Button";
import api from "../../../api";

const STATUS_OPTIONS = [
  { label: "Unpaid", value: "UNPAID" },
  { label: "Paid", value: "PAID" },
];

export default function InvoiceManagementPage() {
  const navigate = useNavigate();
  const [currentStatus, setCurrentStatus] = useState("UNPAID");
  const [paidInvoiceDate, setPaidInvoiceDate] = useState("");
  const [invoices, setInvoices] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchInvoices = async () => {
      setIsLoading(true);
      setError("");

      try {
        const response = await api.get("/invoices");
        setInvoices(Array.isArray(response.data) ? response.data : []);
      } catch (fetchError) {
        setInvoices([]);
        setError(fetchError.response?.data?.message || "Unable to load invoices");
      } finally {
        setIsLoading(false);
      }
    };

    fetchInvoices();
  }, []);

  const filteredInvoices = useMemo(() => {
    return invoices.filter((invoice) => {
      if (invoice.invoiceStatus !== currentStatus) return false;
      if (currentStatus !== "PAID" || !paidInvoiceDate) return true;

      return getInvoiceDateValue(invoice) === paidInvoiceDate;
    });
  }, [currentStatus, invoices, paidInvoiceDate]);

  const invoiceTotal = useMemo(() => {
    return filteredInvoices.reduce((sum, invoice) => sum + Number(invoice.totalPay ?? 0), 0);
  }, [filteredInvoices]);

  return (
    <>
      <div className="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-4 mb-6">
        <div>
          <h1 className="text-3xl font-bold text-main-navy">Invoices</h1>
          <p className="text-gray-500">View invoices by paid and unpaid status.</p>
        </div>
        <div className="bg-white border border-gray-300 rounded-xl px-4 py-3 min-w-44">
          <p className="text-sm text-gray-500">{currentStatus === "PAID" ? "Paid Total" : "Unpaid Total"}</p>
          <p className="text-2xl font-bold text-main-navy">${invoiceTotal.toFixed(2)}</p>
        </div>
      </div>

      <div className="mb-7 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
        <nav className="bg-dark-cream font-bold inline-flex w-fit space-x-1 p-1 rounded-2xl">
          {STATUS_OPTIONS.map((option) => (
            <button
              key={option.value}
              type="button"
              onClick={() => setCurrentStatus(option.value)}
              className={`horizontal-nav-bar ${currentStatus === option.value ? "horizontal-nav-bar-active" : ""}`}
            >
              {option.label}
            </button>
          ))}
        </nav>

        {currentStatus === "PAID" ? (
          <div className="flex flex-col gap-1 sm:items-end">
            <label htmlFor="paid-invoice-date" className="text-sm font-semibold text-gray-500">
              Paid invoice date
            </label>
            <input
              id="paid-invoice-date"
              type="date"
              value={paidInvoiceDate}
              onChange={(event) => setPaidInvoiceDate(event.target.value)}
              className="rounded-xl border border-gray-300 bg-white px-4 py-2 font-semibold text-main-navy outline-none transition focus:border-main-navy focus:ring-2 focus:ring-main-navy/10"
            />
          </div>
        ) : ""}
      </div>

      <div className="bg-white border border-gray-300 rounded-xl overflow-hidden">
        <div className="grid grid-cols-12 bg-input-bg px-4 py-3 text-sm font-bold text-gray-500">
          <div className="col-span-2">Invoice</div>
          <div className="col-span-2 text-right">Before Discount</div>
          <div className="col-span-2 text-right">Discount</div>
          <div className="col-span-2 text-right">Total</div>
          <div className="col-span-3 text-center">Status</div>
          <div className="col-span-1 text-right">View</div>
        </div>

        {isLoading ? (
          <div className="px-4 py-6 text-gray-500">Loading invoices...</div>
        ) : error ? (
          <div className="px-4 py-6 text-red-600">{error}</div>
        ) : filteredInvoices.length === 0 ? (
          <div className="px-4 py-6 text-gray-500">No {currentStatus.toLowerCase()} invoices to show.</div>
        ) : (
          filteredInvoices.map((invoice) => (
            <div key={invoice.invoiceId} className="grid grid-cols-12 items-center px-4 py-3 border-t border-gray-200">
              <div className="col-span-2 font-semibold text-main-navy">#{invoice.invoiceId}</div>
              <div className="col-span-2 text-right">${Number(invoice.payBeforeDiscount ?? 0).toFixed(2)}</div>
              <div className="col-span-2 text-right">${Number(invoice.discountAmount ?? 0).toFixed(2)}</div>
              <div className="col-span-2 text-right font-semibold">${Number(invoice.totalPay ?? 0).toFixed(2)}</div>
              <div className="col-span-3 text-center">
                <span className={`rounded-2xl px-3 py-1 text-xs font-semibold ${getStatusClass(invoice.invoiceStatus)}`}>
                  {invoice.invoiceStatus}
                </span>
              </div>
              <div className="col-span-1 flex justify-end">
                <Button
                  variant="outline"
                  className="py-1"
                  onClick={() => navigate(`/admin/invoices/${invoice.invoiceId}`)}
                >
                  View
                </Button>
              </div>
            </div>
          ))
        )}
      </div>
    </>
  );
}

function getStatusClass(status) {
  return status === "PAID" ? "bg-green-100 text-green-700" : "bg-yellow-100 text-yellow-700";
}

function getInvoiceDateValue(invoice) {
  const invoiceDate = invoice.createdAt ?? invoice.invoiceTime;
  if (!invoiceDate) return "";

  const date = new Date(invoiceDate);
  if (Number.isNaN(date.getTime())) return "";

  const offsetDate = new Date(date.getTime() - date.getTimezoneOffset() * 60000);
  return offsetDate.toISOString().slice(0, 10);
}
