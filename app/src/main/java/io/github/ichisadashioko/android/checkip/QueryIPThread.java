package io.github.ichisadashioko.android.checkip;

import android.graphics.Color;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QueryIPThread extends Thread {
    public static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
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

            StringBuilder sb = new StringBuilder();

            byte[] buffer = new byte[1024];
            while (true) {
                int numberOfReadBytes = inputStream.read(buffer);
                if (numberOfReadBytes < 1) {
                    break;
                }

                char[] hexChars = new char[numberOfReadBytes * 2];
                for (int i = 0; i < numberOfReadBytes; i++) {
                    int v = buffer[i] & 0xff;
                    hexChars[i * 2] = HEX_ARRAY[v >>> 4];
                    hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0f];
                }

                sb.append(new String(hexChars));
            }

            System.out.println(sb.toString());
            app.ipView.changeStatusColor(Color.GREEN);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            app.ipView.changeStatusColor(Color.RED);
        }
    }
}
