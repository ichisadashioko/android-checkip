package io.github.ichisadashioko.android.checkip;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity {

    public CheckIPView ipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow()
                    .setFlags(
                            WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the status bar is hidden, so
            // hide that too if necessary.
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }

        setContentView(R.layout.activity_main);

        ipView = findViewById(R.id.ipview);
    }

    public void onIpViewClicked(View v) {
        if (ipView.statusColor == Color.BLACK) {
            // TODO initialize request to external server to get our current IP
            ipView.changeStatusColor(Color.BLUE);
            (new QueryIPThread(this)).start();
        } else if ((ipView.statusColor == Color.GREEN) && (!ipView.showReponse)) {
            ipView.showReponse = true;
            ipView.invalidate();
        }
    }
}
