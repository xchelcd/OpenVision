package com.idax.openvision.Extra

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class Permission {

    companion object {
        val REQUEST_CODE_PERMISSIONS = 15

        fun cameraPermissionsGranted(context: Context, permission: String) =
            PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                context,
                permission
            )

        fun requestPermission(fragment: Fragment, permissions: Array<String>) {
            val pDenied: MutableList<String> = ArrayList()

            for (p: String in permissions)
                if (PackageManager.PERMISSION_DENIED == fragment.context?.let {
                        ContextCompat.checkSelfPermission(
                            it, p)
                    }) pDenied.add(p)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                fragment.requestPermissions(pDenied.toTypedArray(), REQUEST_CODE_PERMISSIONS)
                /*requestPermissions(
                    activity,
                    pDenied.toTypedArray(), REQUEST_CODE_PERMISSIONS)*/
            else Toast.makeText(fragment.context, "Permission required", Toast.LENGTH_SHORT).show()
        }
    }
    /*
    public static boolean checkPermission(Context context, String permission){
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission));
    }

    public static void requestPermission(Activity activity, String[] permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    CODE_PERMISSIONS_GENERAL);
        else Toast.makeText(activity, "No se pudo completar los permisos requeridos.", Toast.LENGTH_SHORT).show();
    }
    */
}