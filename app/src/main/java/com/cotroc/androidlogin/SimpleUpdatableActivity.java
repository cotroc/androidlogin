package com.cotroc.androidlogin;

import java.util.ArrayList;

/**
 * Created by Cotroc on 22/08/2017.
 */

public interface SimpleUpdatableActivity {
    void update(ArrayList<String> results);
    void progress(String message);
}
