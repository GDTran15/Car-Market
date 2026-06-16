import { Link, useParams } from "react-router-dom";
import CustomerMenuNavbar from "../../component/CustomerMenuNavBar";
import { useEffect, useState } from "react";
import OrderCard from "../../component/OrderCard";
import Button from "../../component/Button";
import { publicApi } from "../../api";

export default  function CustomerOrderPage(){
    const { token } = useParams();
    const [diningSessionId,setDiningSessionId] = useState(null);
    const [orderList,setOrderList] = useState([]);
    const [activeInvoice, setActiveInvoice] = useState(null);

    console.log({token})

  useEffect(() => {
  const authenticateDiningSession = async () => {
    try {
      const response = await publicApi.get("/dining-sessions", {
        params: { tableQrToken: token }
      });

      
      setDiningSessionId(response.data.dinningSessionId); 
    } catch (error) {
      console.log(error.response);
    }
  };

  if (token) {
    authenticateDiningSession();
  }
}, [token]);

useEffect(() => {
  if (!diningSessionId) return;

  const handleGetOrder = async () => {
    try {
      const response = await publicApi.get("/orders", {
        params: { diningSessionId }
      });
      setOrderList(response.data);
    } catch (error) {
      console.log(error.response);
    }
  };

  handleGetOrder();
}, [diningSessionId]);

useEffect(() => {
  if (!diningSessionId) return;

  const fetchActiveInvoice = async () => {
    try {
      const response = await publicApi.get("/invoices/active", {
        params: { diningSessionId },
      });
      setActiveInvoice(response.status === 204 ? null : response.data);
    } catch (error) {
      console.log(error.response);
      setActiveInvoice(null);
    }
  };

  fetchActiveInvoice();
}, [diningSessionId]);

    
    
    return(<>
          <CustomerMenuNavbar page={"order"} token={token}/>
            <div className="max-w-7xl mx-auto p-4 sm:p-6 lg:p-8">
                {activeInvoice?.invoiceId ? (
                    <div className="mb-5 bg-white rounded-xl border border-gray-300 p-6">
                        <h2 className="text-xl font-bold text-main-navy">Invoice is ready</h2>
                        <p className="mt-2 text-gray-500">
                            Ordering is paused for this table. You can continue ordering only if staff cancels the invoice.
                        </p>
                        <Link to={`/customer/invoice/${token}/${activeInvoice.invoiceId}`} className="mt-5 inline-block">
                            <Button variant="navy">
                                View Invoice
                            </Button>
                        </Link>
                    </div>
                ) : ""}
                <div className="flex flex-col gap-3">
                    {orderList.map((order) => (
                
                    <OrderCard order={order}/>
                ))}
                </div>
                
                
                
            </div>

    </>)
    
}
