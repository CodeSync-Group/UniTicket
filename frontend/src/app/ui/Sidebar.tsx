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
        <div className="inline-flex  items-center justify-between">
          <Image
            src={logo}
            alt="Hola"
            width={50}
            height={50}
            className={`size-10  text-4xl block rounded cursor-pointer  float-left mr-2 duration-500 ${open}`}
            onClick={() => setOpen(!open)}
          />
          {/* <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className={`size-10  text-4xl  rounded cursor-pointer block float-left mr-2 duration-500 ${
              open && "rotate-[360deg]"
            }`}
            onClick={() => setOpen(!open)}
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M16.5 6v.75m0 3v.75m0 3v.75m0 3V18m-9-5.25h5.25M7.5 15h3M3.375 5.25c-.621 0-1.125.504-1.125 1.125v3.026a2.999 2.999 0 0 1 0 5.198v3.026c0 .621.504 1.125 1.125 1.125h17.25c.621 0 1.125-.504 1.125-1.125v-3.026a2.999 2.999 0 0 1 0-5.198V6.375c0-.621-.504-1.125-1.125-1.125H3.375Z"
            />
          </svg> */}
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
