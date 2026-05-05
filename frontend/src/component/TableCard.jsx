import { PiChairFill } from "react-icons/pi";
import Button from "./Button";
import { RiDeleteBin5Fill } from "react-icons/ri";
import { useState } from "react";
import { IoMdClose } from "react-icons/io";
import api from "../api";

export default function TableCard({restaurantTableId,restaurantTableNumber,restaurantTableStatus,capacity,deleteTable,onCreateInvoice}){
    const[showQrCode,setShowQrCode] = useState(false);
    const[showTableOptions,setShowTableOptions] = useState(false);
    const[qrCode,setQrCode] = useState();


       const fetchQrcode = async () => {
        try {
            const response = await api.get(`tables/${restaurantTableId}/qrCode`, {
                responseType: "blob"
            }
            );
            const imageUrl = URL.createObjectURL(response.data);
            console.log(response);
            setQrCode(imageUrl);
            setShowQrCode(true);
            setShowTableOptions(false);
        } catch (error) {
            console.log(error.response)
        }
       }

       const handleTableClick = () => {
        if (restaurantTableStatus) {
            fetchQrcode();
            return;
        }

        setShowTableOptions(true);
       }

       const handleCreateInvoice = () => {
        onCreateInvoice?.(restaurantTableId);
        setShowTableOptions(false);
       }

       
    return(<>
        <div onClick={handleTableClick} className="bg-white h-auto py-6 flex flex-col items-center rounded-xl px-10 border-gray-300 border gap-2 hover:opacity-60 cursor-pointer">
            <PiChairFill size={25} className="text-amber-700"/>
            <h5 className="font-semibold text-main-navy">Table {restaurantTableNumber}</h5>
            <p className="text-gray-500">{capacity} seats</p>
            <span className={`rounded-2xl px-2 py-1 text-xs font-semibold ${restaurantTableStatus ? "bg-second-cream text-main-navy" : "bg-gray-200 text-gray-500"}`}>
                {restaurantTableStatus ? "Available" : "Unavailable"}
            </span>
            <Button className={"bg-input-bg border border-gray-300 w-full hover:bg-main-navy hover:text-white"} onClick={(event) => {
                event.stopPropagation();
                deleteTable(restaurantTableId);
            }}><RiDeleteBin5Fill size={23}/></Button>
        </div>
       {showTableOptions ? <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
            <div className="bg-white rounded-xl border border-gray-300 p-6 w-80">
                <div className="flex items-center justify-between mb-5">
                    <h5 className="font-bold text-xl text-main-navy">Table {restaurantTableNumber}</h5>
                    <IoMdClose onClick={() => setShowTableOptions(false)} size={24} className="cursor-pointer text-gray-500 hover:text-main-navy"/>
                </div>
                <div className="flex flex-col gap-3">
                    <Button className={"bg-input-bg border border-gray-300 w-full hover:bg-main-navy hover:text-white"} onClick={fetchQrcode}>QR Code</Button>
                    <Button className={"bg-main-navy text-white w-full hover:opacity-80"} onClick={handleCreateInvoice}>Create Invoice</Button>
                </div>
            </div>
       </div> : ""}
       {showQrCode ? <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 ">
            <IoMdClose onClick={() => setShowQrCode(false)} size={30} className="absolute top-28 right-130 text-white cursor-pointer"/>
             <img  src={qrCode}
  alt="QR Code" />
        </div> : ""}
        
    </>)
}
