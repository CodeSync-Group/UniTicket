import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";

import { useState } from "react";
import Link from "next/link";

export default function Login() {
  return (
    <div className="flex flex-col w-auto justify-start gap-5 items-center justify-center">
      <div className="flex flex-col gap-5 w-auto items-center justify-center p-10 rounded-2xl ring-1 ring-gray-300 ">
        <span className="text-xl">University Ticket System</span>
        <input
          type="text"
          name="username"
          id="username"
          className="block w-full rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-cyan-600 outline-none sm:text-sm sm:leading-6"
          placeholder="Username"
        />
        <input
          type="password"
          name="password"
          id="password"
          className="block w-full rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-cyan-600 outline-none sm:text-sm sm:leading-6"
          placeholder="Password"
        />
        <section className="flex flex-row w-full justify-center items-center">
          <div className="flex w-3/4 gap-1 ">
            <input type="checkbox" className="accent-orange-600" />
            <span className="text-sm">Remember me</span>
          </div>
          <div className="w-1/4 flex justify-end ">
            <button className="block rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 ">
              Login
            </button>
          </div>
        </section>
        <section className="flex flex-row w-full justify-center">
          <div className="flex w-2/5 gap-1 justify-start text-sm">
            <a className="flex flex-row w-full text-cyan-600 underline" href="">
              Register now
            </a>
          </div>
          <span className="w-3/5 flex justify-end text-sm ">
            <a href="" className="text-gray-400">
              Forgot password?
            </a>
          </span>
        </section>

        <div className="flex w-full items-center rounded-full ">
          <div className="flex-1 border-b border-gray-300"></div>
          <span className="text-gray-400 text-sm px-5">or</span>
          <div className="flex-1 border-b border-gray-300"></div>
        </div>

        <Link
          href={""}
          className="flex w-full items-center justify-center rounded bg-google-red p-2 text-slate-50"
        >
          <div className="flex w-auto items-center justify-center flex-row gap-5">
            <FontAwesomeIcon icon={faGoogle} />
            <span className="">Login with Google</span>
          </div>
        </Link>
      </div>
    </div>
  );
}
