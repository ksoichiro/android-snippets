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

package com.snippet.app;

import com.snippet.R;
import com.snippet.widget.Tab1Fragment;
import com.snippet.widget.Tab2Fragment;
import com.snippet.widget.Tab3Fragment;
import com.snippet.widget.actionbar.ActionBarFragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import java.util.HashMap;

public class ActionBarCompatActivity extends ActionBarFragmentActivity
        implements TabHost.OnTabChangeListener {

    private TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    private TabInfo mLastTab = null;

    private class TabInfo {

        private String mTag;
        private Class<? extends Fragment> mClass;
        private Bundle mArgs;
        private Fragment mFragment;

        public TabInfo(final String tag, final Class<? extends Fragment> clazz,
                final Bundle args) {
            mTag = tag;
            mClass = clazz;
            mArgs = args;
        }

    }

    private class TabFactory implements TabContentFactory {

        private final Context mContext;

        public TabFactory(final Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(final String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 1: Inflate layout
        setContentView(R.layout.activity_action_bar_compat);
        // Step 2: Setup TabHost
        initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            // set the tab as per the saved state
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        // save the tab selected
        outState.putString("tab", mTabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTabChanged(final String tag) {
        TabInfo newTab = this.mapTabInfo.get(tag);
        if (mLastTab != newTab) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.mFragment != null) {
                    ft.detach(mLastTab.mFragment);
                }
            }
            if (newTab != null) {
                if (newTab.mFragment == null) {
                    newTab.mFragment = Fragment.instantiate(this,
                            newTab.mClass.getName(), newTab.mArgs);
                    ft.add(R.id.realtabcontent, newTab.mFragment, newTab.mTag);
                } else {
                    ft.attach(newTab.mFragment);
                }
            }

            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }
    }

    private void initialiseTabHost(final Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.empty_divider);

        View tab = View.inflate(this, R.layout.tab, null);
        ((TextView) tab.findViewById(R.id.title)).setText("Tab 1");
        ((ImageView) tab.findViewById(R.id.icon)).setImageResource(R.drawable.tab1_icon);
        addTab(mTabHost.newTabSpec("Tab1").setIndicator(tab),
                new TabInfo("Tab1", Tab1Fragment.class, args));

        tab = View.inflate(this, R.layout.tab, null);
        ((TextView) tab.findViewById(R.id.title)).setText("Tab 2");
        ((ImageView) tab.findViewById(R.id.icon)).setImageResource(R.drawable.tab2_icon);
        addTab(mTabHost.newTabSpec("Tab2").setIndicator(tab),
                new TabInfo("Tab2", Tab2Fragment.class, args));

        tab = View.inflate(this, R.layout.tab, null);
        ((TextView) tab.findViewById(R.id.title)).setText("Tab 3");
        ((ImageView) tab.findViewById(R.id.icon)).setImageResource(R.drawable.tab3_icon);
        addTab(mTabHost.newTabSpec("Tab3").setIndicator(tab),
                new TabInfo("Tab3", Tab3Fragment.class, args));

        // Default to first tab
        onTabChanged("Tab1");

        mTabHost.setOnTabChangedListener(this);
    }

    private void addTab(final TabHost.TabSpec tabSpec, final TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(new TabFactory(this));
        String tag = tabSpec.getTag();

        // Check to see if we already have a mFragment for this tab, probably
        // from a previously saved state. If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.mFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.mFragment != null && !tabInfo.mFragment.isDetached()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.mFragment);
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        mapTabInfo.put(tabInfo.mTag, tabInfo);
        mTabHost.addTab(tabSpec);
    }
}
