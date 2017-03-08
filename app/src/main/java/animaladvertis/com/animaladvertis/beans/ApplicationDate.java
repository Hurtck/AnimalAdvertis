package animaladvertis.com.animaladvertis.beans;

import android.app.Application;

/**
 * Created by 47321 on 2017/3/6 0006.
 */

public class ApplicationDate extends Application {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username ;

}
