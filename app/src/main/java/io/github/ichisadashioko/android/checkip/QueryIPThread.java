package io.github.ichisadashioko.android.checkip;

import android.graphics.Color;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QueryIPThread extends Thread {
    public final MainActivity app;

    public QueryIPThread(MainActivity app) {
        this.app = app;
    }

    public void run() {
        try {
            URL url = new URL("http://ipinfo.io/json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setInstanceFollowRedirects(false);

            int status = con.getResponseCode();
            System.out.print("status: ");
            System.out.println(status);
            InputStream inputStream = con.getInputStream();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            while (true) {
                int numberOfReadBytes = inputStream.read(buffer);
                if (numberOfReadBytes < 1) {
                    break;
                }

                byteArrayOutputStream.write(buffer, 0, numberOfReadBytes);
            }

            String jsonStr = new String(byteArrayOutputStream.toByteArray());
            JSONObject jsonObject = new JSONObject(jsonStr);
            System.out.println(jsonObject);

            Object ipValue = jsonObject.get("ip");
            if (ipValue instanceof String) {
                app.ipView.ipinfoResponseHexString = (String) ipValue;
                app.ipView.changeStatusColor(Color.GREEN);
            } else {
                throw new Exception("Broken ip checking API");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            app.ipView.changeStatusColor(Color.RED);
        }
    }
}
