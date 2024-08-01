import type { NextAuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import axios from "axios";

export const options: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        username: {
          label: "Username",
          type: "text",
        },
        password: {
          label: "Password",
          type: "password",
        },
      },
      async authorize(credentials) {
        // aqui se verifican las credenciales, deberia ser el post al back
        const res = await axios.post(
          "", // AQUI FALTA CONFIGURAR EL ENDPOINT
          {
            username: credentials?.username,
            password: credentials?.password,
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        const user = res.data;

        if (
          credentials?.username === user.name &&
          credentials?.password === user.password
        )
          return user;
        else return null;
      },
    }),
  ],
  callbacks: {
    async jwt({ token, user }) {
      if (user) token.user = user.role;
      return token;
    },
    async session({ session, token }) {
      if (session?.user) session.user.role = token.role;
      return session;
    },
  },
};
