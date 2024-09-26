"use client";

import Login from "./ui/Login";
import Sidebar from "./ui/Sidebar";
import Image from "next/image";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-row 0">
      <div className="left w-1/2 ">{/* <Sidebar /> */}</div>
      <div className="right w-1/2 justify-center items-center flex p-20 ">
        <Login />
      </div>
    </main>
  );
}
