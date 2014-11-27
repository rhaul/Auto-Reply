package aaremm.com.donttextndrive.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.CheckBox;

import aaremm.com.donttextndrive.R;
import aaremm.com.donttextndrive.config.BApp;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingsFragment extends Fragment {

    @InjectView(R.id.ll_settings_editMsg)LinearLayout ll_editMsg;
    @InjectView(R.id.tv_settings_msg)TextView tv_msg;
    @InjectView(R.id.cb_settings_walk)CheckBox cb_walk;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.inject(this, rootView);
        loadSettings();
        cb_walk.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(boolean check) {
                cb_walk.setChecked(check);
                BApp.getInstance().setSPBoolean("walk", check);
            }
        });
        ll_editMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return rootView;
    }

    private void showDialog() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.edit_message, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.et_edit_msg);
        userInput.setText(BApp.getInstance().getSPString("msg"));
        // set dialog message
        alertDialogBuilder
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                BApp.getInstance().setSPString("msg",userInput.getText().toString());
                                tv_msg.setText(BApp.getInstance().smsTextHeader+"call/sms. "+BApp.getInstance().getSPString("msg"));
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void loadSettings() {
        cb_walk.setChecked(BApp.getInstance().getSPBoolean("walk"));
        tv_msg.setText(BApp.getInstance().smsTextHeader+"call/sms. "+BApp.getInstance().getSPString("msg"));
    }
}
