import { DefaultSession, DefaultUser } from "next-auth";
import { JWT, DefaultJWT } from "next-auth/jwt";
import { decodeAction } from "next/dist/server/app-render/entry-base";

declare module "next-auth" {
  interface Session {
    user: {
      id: string;
      role: string;
    } & DefaultUser;
  }

  interface User extends DefaultUser {
    role: string;
  }
}

declare module "next-auth/jwt" {
  interface JWT extends DefaultJWT {
    role: string;
  }
}