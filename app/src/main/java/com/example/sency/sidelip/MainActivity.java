package com.example.sency.sidelip;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

    private SlideMenu slideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        slideMenu = (SlideMenu) findViewById(R.id.slidemenu);
    }

    public void toggleMenu(View view) {
        slideMenu.toggle();
    }


}
