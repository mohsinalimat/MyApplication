package com.example.vin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.vin.myapplication.MoviesAdapter.context;
import static com.example.vin.myapplication.R.id.listView;

public class fragment_dialog_for_user_lock_set_time_hour extends android.support.v4.app.DialogFragment {

    private EditText mEditText;

    public String designation = "";

    private static String[] hour = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    // Empty constructor required for DialogFragment
    public fragment_dialog_for_user_lock_set_time_hour() {
    }

    public static fragment_dialog_for_user_lock_set_time_hour newInstance(String title) {
        fragment_dialog_for_user_lock_set_time_hour frag = new fragment_dialog_for_user_lock_set_time_hour();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.fragment_dialog_for_user_lock_set_time_hour, container);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            int width = getContext().getResources().getDisplayMetrics().widthPixels;
            TextView txt_test = (TextView) getView().findViewById(R.id.txt_test);
            txt_test.setWidth(width);
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onStart() {
        try {
            super.onStart();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = app_preferences.getString("designation", "default");

            data_fetch();

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        //POJO_User_Hierarchy clickedObj = (POJO_User_Hierarchy) parent.getItemAtPosition(position);
                        //sendBackLockTimeHourResult(clickedObj.getName(), clickedObj.getFirst_name() + " " + clickedObj.getLast_name() + "(" + clickedObj.getDesignation() + ")", "EMP");
                        sendBackLockTimeHourResult(String.valueOf(position), hour[position]);
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });

            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        data_fetch();
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            TextView txt_cancel = (TextView) getView().findViewById(R.id.txt_cancel);
            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //sendBackUserListHirarchyResult("NONE", "NONE", "EMP");
                        dismiss();
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void data_fetch() {
        try {
            Bind_hour_to_listview();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Bind_hour_to_listview() {
        try {
            ListView lv = (ListView) getView().findViewById(listView);
            lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hour));
            lv.setVisibility(View.VISIBLE);

            if (hour.length == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO DATA FOUND..");
            } else {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }

        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface EditLockTimeHourDialogListener {
        void onFinishLockTimeHourDialog(String id, String hh);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackLockTimeHourResult(String id, String hh) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditLockTimeHourDialogListener listener = (EditLockTimeHourDialogListener) getTargetFragment();
        listener.onFinishLockTimeHourDialog(id, hh);
        dismiss();
    }
}