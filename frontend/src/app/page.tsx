import Image from "next/image";
import Login from "./ui/Login";
import Register from "./ui/Register";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-row 0">
      <div className="left w-1/2 bg-cyan-600">aaaa</div>
      <div className="right w-1/2 justify-center items-center flex p-20 ">
        <Login />
      </div>
    </main>
  );
}
