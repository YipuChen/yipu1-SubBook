
/*
 * Copyright (c) 2018 Yipu Chen, CMPUT301. University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of Code of Student Behavior
 *  at University of Alberta.
 *  You can find a copy of the lincense in this project. Otherwise, please contact yipu1@ualberta.ca
 *
 */

package com.example.yipu1_subbook;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Represents a editing dialog.
 * It shows an subscription selected from the list on the main screen.
 * It contains 2 buttons: confirm and cancel
 * Cancel button dismiss the dialog without doing anything.
 * Confirm button resets all atrributes of the selected subscription according
 * to the user's input.
 * Input control is implemented.
 *
 * @author Yipu
 *
 * @version 1.0
 *
 */

public class EditDialog extends Dialog {
    private Context context;
    private onDialogListener listener;
    private Subscription subscription;

    public interface onDialogListener {
        void onEnsure(String name, String date, String amount, String comment);
    }

    public EditDialog(@NonNull Context context,Subscription subscription, onDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.subscription = subscription;
    }



    @Override
    protected void onCreate(Bundle SaveInstanceState) {
        super.onCreate(SaveInstanceState);
        setContentView(R.layout.edit_dialog);
        initView();
        setListener();
    }

    EditText nameText;
    EditText dateText;
    EditText amountText;
    EditText commentText;
    Button confirmButton;
    Button cancelButton;

    private void initView() {
        nameText = findViewById(R.id.editText_Name);
        nameText.setText(subscription.getName());
        dateText = findViewById(R.id.editText_Date);
        dateText.setText(subscription.getDate());
        amountText = findViewById(R.id.editText_Amount);
        amountText.setText(String.valueOf(subscription.getAmount()));
        commentText = findViewById(R.id.editText_Comment);
        commentText.setText(subscription.getComment());
        confirmButton = findViewById(R.id.button_confirm);
        cancelButton = findViewById(R.id.button_cancel);
    }

    private void setListener() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString().trim();
                String date = dateText.getText().toString().trim();
                String amount = amountText.getText().toString().trim();
                String comment = commentText.getText().toString().trim();
                if (checkInput(name, date, amount,comment)) {
                    dismiss();
                    listener.onEnsure(name, date, amount, comment);
                } else {
                    if (name.length() == 0) {
                        nameText.setError("Non empty!");
                    } else if (name.length() > 20) {
                        nameText.setError("Name too long!");
                        //taken from stackoverflow https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
                    } else if (!date.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
                        dateText.setError("Format: yyyy-mm-dd");
                    } else if (amount.length() == 0) {
                        amountText.setError("Non empty!");
                    } else if (comment.length() > 30) {
                        commentText.setError("Comment too long!");
                    }


                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private boolean checkInput(String name, String dateString, String amountString, String comment) {
        if (name.length() == 0 || name.length() > 20 ||
                !dateString.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$") ||
                comment.length() > 30) {
            return false;
        } else {
            return true;
        }
    }


}

