package com.example.cs411project;

import android.app.Application;

public class globalVars extends Application {

    private static int curr_user_id=-1;

    public static void setUser(int i ) {
        curr_user_id=i;
    }

    public static int getUser() {
        return curr_user_id;
    }
}
