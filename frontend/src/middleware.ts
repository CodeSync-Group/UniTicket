import { withAuth, NextRequestWithAuth } from "next-auth/middleware";
import { requestAsyncStorage } from "next/dist/client/components/request-async-storage-instance";
import { NextResponse } from "next/server";
import { requestFormReset } from "react-dom";

export default withAuth(
  function middleware(request: NextRequestWithAuth) {
    console.log(request.nextUrl.pathname);
    console.log();

    if (
      request.nextUrl.pathname.startsWith("/extra") &&
      request.nextauth.token?.role != "admin"
    ) {
      return NextResponse.rewrite(new URL("/denied", request.url));
    }
  },
  {
    callbacks: {
      authorized: ({ token }) => !!token,
    },
  }
);

//estas rutas son de ejemplo por ahora, siguiendo el video.
export const config = { matcher: ["/extra", "/dashboard"] };
