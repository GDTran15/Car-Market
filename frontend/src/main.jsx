import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'

import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import LoginPage from './page/LoginPage.jsx'

import AdminHomePage from './page/admin/AdminHomePage.jsx'
import AdminDashboardPage from './page/admin/AdminDashboardPage.jsx'
import FoodManagementPage from './page/admin/food_management/FoodManagementPage.jsx'
import MenuManagementPage from './page/admin/menu/MenuManagementPage.jsx'
import StaffManagementPage from './page/admin/staff/StaffManagementPage.jsx'
import TableManagementPage from './page/admin/table/TableManagementPage.jsx'
import InvoicePage from './page/admin/invoice/InvoicePage.jsx'
import InvoiceManagementPage from './page/admin/invoice/InvoiceManagementPage.jsx'
import PaymentCancelPage from './page/admin/invoice/PaymentCancelPage.jsx'
import PaymentSuccessPage from './page/admin/invoice/PaymentSuccessPage.jsx'
import MenuDetailPage from './page/admin/menu/MenuDetailPage.jsx'
import CustomerMenuPage from './page/customer/CustomerMenuPage.jsx'
import CustomerOrderPage from './page/customer/CustomerOrderPage.jsx'
import CustomerInvoicePage from './page/customer/CustomerInvoicePage.jsx'
import OrderManagementPage from './page/admin/order/OrderManagementPage.jsx'
import { AuthProvider } from './page/authentication/AuthProvider.jsx'
import ProtectedRoute from './page/authentication/ProtectedRoute.jsx'




const router = createBrowserRouter([
  { path: "/login", element: <LoginPage  /> },
  
  {
    element: <ProtectedRoute/>,
    children: [
      {
        path: "/admin",
        element: <AdminHomePage />,
        children: [
          { index: true, element: <AdminDashboardPage /> },
          { path: "foods", element: <FoodManagementPage /> },
          { path: "menus", element: <MenuManagementPage /> },
          { path: "menus/:menuId", element: <MenuDetailPage /> },
          { path: "staffs", element: <StaffManagementPage /> },
          { path: "tables", element: <TableManagementPage /> },
          { path: "invoices", element: <InvoiceManagementPage /> },
          { path: "invoices/:invoiceId", element: <InvoicePage /> },
          { path: "payment/success", element: <PaymentSuccessPage /> },
          { path: "payment/cancel", element: <PaymentCancelPage /> },
          { path: "orders", element: <OrderManagementPage/>}
        ],
  },
    ]
  },
  
  {
    path: "/customer/menu/:token", element: <CustomerMenuPage/>
  }, {
    path: "/customer/order/:token", element: <CustomerOrderPage/>
  }, {
    path: "/customer/invoice/:token/:invoiceId", element: <CustomerInvoicePage/>
  }, {
    path: "/customer/payment/success", element: <PaymentSuccessPage/>
  }, {
    path: "/customer/payment/cancel", element: <PaymentCancelPage/>
  }
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
    <RouterProvider router={router}/>
    </AuthProvider>
  </StrictMode>,
)
