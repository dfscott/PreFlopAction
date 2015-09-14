package com.poker.dscott.pfatrainer.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.poker.dscott.pfatrainer.App;
import com.poker.dscott.pfatrainer.R;

/**
 * Created by dscott on 9/13/2015.
 */
public class ActionResultDialog extends DialogFragment {

    public static ActionResultDialog newInstance(String message) {
        ActionResultDialog frag = new ActionResultDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String msg = getArguments().getString("message");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(App.getContext().getString(R.string.dialog_title));
        builder.setMessage(msg);
        String okText = App.getContext().getString(R.string.OK);
        builder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // You don't have to do anything here if you just want it dismissed when clicked
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
