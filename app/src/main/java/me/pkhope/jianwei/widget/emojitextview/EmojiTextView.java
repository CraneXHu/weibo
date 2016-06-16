package me.pkhope.jianwei.widget.emojitextview;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wenmingvs on 16/3/9.
 */
public class EmojiTextView extends TextView {


    private final Context mContext;

    public EmojiTextView(Context context) {
        super(context, null);
        mContext = context;
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setText(String text) {
        SpannableStringBuilder result = WeiBoContentTextUtil.getWeiBoContent(text,getContext(),this);
        super.setText(result);
    }
}
