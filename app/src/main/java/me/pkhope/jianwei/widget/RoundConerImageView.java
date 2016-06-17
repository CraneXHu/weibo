package me.pkhope.jianwei.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/17.
 */
public class RoundConerImageView extends ImageView {

    private Bitmap bitmap = null;

    private int radius = 10;
    private int width;
    private int height;

    public RoundConerImageView(Context context) {
        this(context, null);
    }

    public RoundConerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundConerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundConerImageView, defStyleAttr, 0);
        radius = a.getDimensionPixelSize(R.styleable.RoundConerImageView_conerRadius, 10);
        a.recycle();

        super.setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != scaleType.CENTER_CROP) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        bitmap = getBitmapFromDrawable(getDrawable());
        if (bitmap == null){
            return;
        }
        bitmap = createRoundConerImage(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        width = specSize;
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        height = specSize;
    }


    private Bitmap createRoundConerImage(Bitmap source)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
