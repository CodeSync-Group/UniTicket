import type { NextAuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

export const options: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        username: {
          label: "Username",
          type: "text",
          placeholder: "Hola q onda",
        },
        password: {
          label: "Password",
          type: "password",
          placeholder: "Your pass here",
        },
      },
      async authorize(credentials) {
        // aqui se verifican las credenciales, deberia ser el post al back
        const user = { id: "42", name: "Dave", password: "nextauth" };
        if (
          credentials?.username === user.name &&
          credentials?.password === user.password
        )
          return user;
        else return null;
      },
    }),
  ],
};
