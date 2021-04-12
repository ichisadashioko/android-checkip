package io.github.ichisadashioko.android.checkip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class CheckIPView extends View {
    public int statusColor = Color.BLACK;
    public String ipinfoResponseHexString = null;

    public Typeface responseTypeFace = Typeface.MONOSPACE;
    public int responseFontSize = 20;

    public static int FindSuitableFontSize(String text, int widthLimit, Typeface typeface) {
        int retval = 0;

        if (text.length() > 0) {
            retval = widthLimit / text.length();

            TextPaint textPaint = new TextPaint();
            textPaint.setTypeface(typeface);
            textPaint.setTextSize(retval);

            float renderedTextWidth = textPaint.measureText(text);
            while ((renderedTextWidth > ((float) widthLimit)) & (retval > 0)) {
                retval--;
                textPaint.setTextSize(retval);
                renderedTextWidth = textPaint.measureText(text);
            }
        }

        return retval;
    }

    public CheckIPView(Context context) {
        super(context);
    }

    public CheckIPView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckIPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void changeStatusColor(int color) {
        this.statusColor = color;

        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void setIPINFOResponse(String hexStr) {
        this.statusColor = Color.WHITE;
        this.ipinfoResponseHexString = hexStr;

        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (this.ipinfoResponseHexString == null) {
            Paint p = new Paint();
            p.setColor(this.statusColor);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(new Rect(0, 0, this.getWidth(), this.getHeight()), p);
        } else {
            Paint p = new Paint();
            p.setColor(this.statusColor);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(new Rect(0, 0, this.getWidth(), this.getHeight()), p);

            p = new Paint();
        }
    }
}
