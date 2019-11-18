package android.example.chitchat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DataWarningDialog extends DialogFragment {

    public interface NoticeDialogListener {
        public void onDialogPostiveClick(DialogFragment dialog);

        public void onDialogNegitiveClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (NoticeDialogListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString() + "must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("My Data Warning").setPositiveButton("OK, Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPostiveClick(DataWarningDialog.this);
            }
        }).setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegitiveClick(DataWarningDialog.this);
            }
        });

        return builder.create();
    }
}
