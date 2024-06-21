import { apiSlice } from "../apiSlice";

export const authApi = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    login: builder.mutation({
      query: (data) => ({
        url: "identity/auth/token",
        method: "POST",
        body: data,
      }),
    }),
  }),
});
export const { useLoginMutation } = authApi;
