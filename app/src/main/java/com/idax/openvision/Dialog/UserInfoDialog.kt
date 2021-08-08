package com.idax.openvision.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.idax.openvision.R

class UserInfoDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        val v: View = LayoutInflater.from(context).inflate(R.layout.dialog_user_info, null, false)
        inits(v)
        dialog.setView(v)
        val alertDialog: AlertDialog = dialog.create()


        alertDialog.show()
        return alertDialog
    }

    private fun inits(v: View) {

    }
}

/*
onCreateDialog{
	AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
	View v = LayoutInflater.from(getContext()).inflate(R.layout.xml, null, false);
	inits(v)
	dialog.setView(v)
	AlertDialog alertDialog = dialog.create();
	alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
	alertDialog.getWindow().setBackground(new ColorDrawable(Color.TRANSPARENT));
	AlertDialog.show();
	return alertDialog;
}
 */