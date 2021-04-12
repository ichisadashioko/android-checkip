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

    public int measuredWidth = 0;
    public int measureHeight = 0;
    public float textLeftPos = 0;
    public float textBottomPos = 0;
    public TextPaint textPaint = null;
    public int textFontSize = 0;
    public String ipinfoResponseHexString = null;
    public boolean showReponse = false;

    public Typeface responseTypeFace = Typeface.MONOSPACE;

    public static int FindSuitableFontSize(String text, int widthLimit, Typeface typeface) {
        int retval = 0;

        if ((text == null) || (text.length() > 0)) {
            retval = (widthLimit / text.length()) * 2;

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

    public void measureTextAssets() {
        this.measuredWidth = this.getWidth();
        this.measureHeight = this.getHeight();

        this.textFontSize =
                FindSuitableFontSize(
                        this.ipinfoResponseHexString, this.measuredWidth, responseTypeFace);
        this.textPaint = new TextPaint();
        this.textPaint.setTypeface(responseTypeFace);
        this.textPaint.setTextSize(this.textFontSize);
        this.textPaint.setColor(Color.BLACK);

        float renderedTextWidth = this.textPaint.measureText(this.ipinfoResponseHexString);
        this.textLeftPos = (((float) this.measuredWidth) - renderedTextWidth) / 2f;
        this.textLeftPos = Math.max(0, this.textLeftPos);

        this.textBottomPos = (((float) this.measureHeight) - ((float) this.textFontSize)) / 2f;
        this.textBottomPos = this.textBottomPos + this.textFontSize;
        this.textBottomPos = Math.min(this.measureHeight, this.textBottomPos);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (showReponse) {
            if ((this.measuredWidth != this.getWidth())
                    || (this.measureHeight != this.getHeight())) {
                this.measureTextAssets();
            }

            Paint p = new Paint();
            p.setColor(Color.WHITE);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(new Rect(0, 0, this.getWidth(), this.getHeight()), p);
            canvas.drawText(
                    this.ipinfoResponseHexString, this.textLeftPos, this.textBottomPos, textPaint);
        } else {
            Paint p = new Paint();
            p.setColor(this.statusColor);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(new Rect(0, 0, this.getWidth(), this.getHeight()), p);
        }
    }
}
