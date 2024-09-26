"use client";

import { UniTicket } from "@/assets/imgs";
import { useState } from "react";

import Image from "next/image";
import logo from "../../assets/imgs/logo.jpeg";

export default function Sidebar() {
  const [open, setOpen] = useState(true);

  return (
    <div className="flex ">
      <div
        className={` bg-gradient-to-l from-cyan-600 to-blue-600 h-screen p-5 pt-8 ${
          open ? "w-72" : "w-20"
        } duration-300 relative`}
      >
        <div className="inline-flex items-center justify-between gap-9">
          <Image
            src={logo}
            alt="Hola"
            width={50}
            height={50}
            className={`size-10  text-4xl block rounded cursor-pointer  float-left duration-500 ${open}`}
            onClick={() => setOpen(!open)}
          />
          <h1
            className={`text-white origin-left font-medium text-3xl duration-300 ${
              !open && "scale-0"
            }`}
          >
            UniTicket
          </h1>
        </div>
      </div>
    </div>
  );
}

/*

Logo
Home 
Tickets
Stats

*/
