package io.github.ichisadashioko.android.checkip;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public CheckIPView ipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipView = findViewById(R.id.ipview);
    }

    public void onIpViewClicked(View v) {
        if (ipView.statusColor == Color.BLACK) {
            // TODO initialize request to external server to get our current IP
            ipView.changeStatusColor(Color.YELLOW);
            (new QueryIPThread(this)).start();
        }
    }
}
