package com.hw.dpf.information.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class InformationActivity extends ListActivity {

    private List<ResolveInfo> mApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager pm = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = pm.queryIntentActivities(mainIntent, 0);
        Collections.sort(mApps, new ResolveInfo.DisplayNameComparator(pm));

        setListAdapter(new ArrayAdapter<ResolveInfo>(this, android.R.layout.simple_list_item_1, mApps) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(getItem(position).loadLabel(getPackageManager()));
                return view;
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ResolveInfo info = mApps.get(position);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
    }
}
