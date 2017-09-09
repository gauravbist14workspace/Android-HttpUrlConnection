package com.example.narutoduzumaki.httpurlconnection.Views.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.narutoduzumaki.httpurlconnection.R;

/**
 * Created by Naruto D. Uzumaki on 06-09-2017.
 */

public class CustomDialogFragment extends DialogFragment {

    private Context mContext;
    EditText editText;
    AlertDialog.Builder builder;

    private OnDialogSubmitListener listener;

    public interface OnDialogSubmitListener {
        void onSubmitClicked(String url);

        void onCancelClicked();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment, null, false);

        builder = new AlertDialog.
                Builder(mContext);
        builder.setView(view);

        editText = (EditText) view.findViewById(R.id.url);

        builder.setTitle("Fill this field");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    listener.onSubmitClicked(editText.getText().toString());
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mContext, "Please provide the IPAddress.", Toast.LENGTH_SHORT).show();
                if (listener != null)
                    listener.onCancelClicked();
            }
        });

        return builder.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
//        listener = (OnDialogSubmitListener) getTargetFragment();
        listener = (OnDialogSubmitListener) ((Activity) mContext);
    }
}
