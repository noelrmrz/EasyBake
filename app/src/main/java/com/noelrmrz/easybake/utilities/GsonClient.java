package com.noelrmrz.easybake.utilities;

import com.google.gson.Gson;

public class GsonClient {
    private static final Gson gson = new Gson();

    public static Gson getGsonClient() {
        return gson;
    }
}
