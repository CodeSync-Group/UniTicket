import Link from "next/link";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";
export default function Register() {
  return (
    <div className="flex flex-col gap-5 w-auto justify-center p-10 rounded-2xl ring-1 ring-gray-300 gap-5 text-sm">
      <div className="flex items-center justify-center">
        <span className="text-xl">University Ticket System</span>
      </div>
      <div className="flex flex-row gap-5 items-center justify-center">
        <div className="flex flex-col gap-3 w-1/2">
          <label htmlFor="firstName">First Name</label>
          <input
            name="firstName"
            type="text"
            className="block w-auto rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-cyan-600 outline-none sm:text-sm sm:leading-6"
            placeholder="Name"
          />
        </div>
        <div className="flex flex-col gap-3 w-1/2">
          <label htmlFor="lastName">Last Name</label>
          <input
            name="lastName"
            type="text"
            className="block w-auto rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-cyan-600 outline-none sm:text-sm sm:leading-6"
            placeholder="Lastname"
          />
        </div>
      </div>
      <div className="flex flex-col gap-3">
        <label htmlFor="username">Username</label>
        <input
          type="text"
          name="username"
          placeholder="Username"
          className="block w-auto rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-cyan-600 outline-none sm:text-sm sm:leading-6"
        />
        <label htmlFor="email">Email addres</label>
        <input
          type="email"
          name="email"
          placeholder="Email addres"
          className="block w-auto rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-cyan-600 outline-none sm:text-sm sm:leading-6"
        />
        <label htmlFor="password">Password</label>
        <input
          type="password"
          name="password"
          placeholder="Password"
          className="block w-auto rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-cyan-600 outline-none sm:text-sm sm:leading-6"
        />
      </div>
      <a className="flex flex-row w-full text-cyan-600 underline" href="">
        Already have an acount?
      </a>
      <div className="flex flex-col gap-5">
        <div className="flex w-3/4 gap-3">
          <input type="checkbox" className="accent-orange-600" />
          <span className="text-sm">
            I do accept the <u>Terms and Conditions</u> of your site
          </span>
        </div>
        <button className="text-m block rounded-md border-0 py-1.5 px-5 text-gray-900 ring-1 ring-inset ring-gray-300">
          Register Now
        </button>
      </div>

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
  );
}
