/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.snippet.util;

import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkUtils {
    public static final Pattern URL_PATTERN =
            Pattern.compile("((https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+))");

    public interface OnClickListener {
        void onLinkClicked(final String link);

        void onClicked();
    }

    static class SensibleUrlSpan extends URLSpan {
        /** Pattern to match. */
        private Pattern mPattern;

        public SensibleUrlSpan(String url, Pattern pattern) {
            super(url);
            mPattern = pattern;
        }

        public boolean onClickSpan(View widget) {
            boolean matched = mPattern.matcher(getURL()).matches();
            if (matched) {
                super.onClick(widget);
            }
            return matched;
        }
    }

    static class SensibleLinkMovementMethod extends LinkMovementMethod {

        private boolean mLinkClicked;

        private String mClickedLink;

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP) {
                mLinkClicked = false;
                mClickedLink = null;
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    SensibleUrlSpan span = (SensibleUrlSpan) link[0];
                    mLinkClicked = span.onClickSpan(widget);
                    mClickedLink = span.getURL();
                    return mLinkClicked;
                }
            }
            super.onTouchEvent(widget, buffer, event);

            return false;
        }

        public boolean isLinkClicked() {
            return mLinkClicked;
        }

        public String getClickedLink() {
            return mClickedLink;
        }

    }

    public static void autoLink(final TextView view, final OnClickListener listener) {
        autoLink(view, listener, null);
    }

    public static void autoLink(final TextView view, final OnClickListener listener,
            final String patternStr) {
        String text = view.getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Spannable spannable = new SpannableString(text);

        Pattern pattern;
        if (TextUtils.isEmpty(patternStr)) {
            pattern = URL_PATTERN;
        } else {
            pattern = Pattern.compile(patternStr);
        }
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            SensibleUrlSpan urlSpan = new SensibleUrlSpan(matcher.group(1), pattern);
            spannable.setSpan(urlSpan, matcher.start(1), matcher.end(1),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        view.setText(spannable, TextView.BufferType.SPANNABLE);

        final SensibleLinkMovementMethod method = new SensibleLinkMovementMethod();
        view.setMovementMethod(method);
        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (method.isLinkClicked()) {
                        listener.onLinkClicked(method.getClickedLink());
                    } else {
                        listener.onClicked();
                    }
                }
            });
        }
    }

}
