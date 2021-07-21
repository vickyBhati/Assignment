package com.example.assignment.presnter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.assignment.R;

import java.io.ByteArrayOutputStream;


public  class BaseActivity extends AppCompatActivity {

//    SpotsDialog mProgressDialog;
    //AlertDialog  mProgressDialog;

    public static final int REQUEST_CAPTURE = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public static final int REQUEST_VIDEO = 1003;


    public Uri captureMediaFile = null;

    ProgressDialog pDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("start","onCreate");

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("start","onResume");

    }



//    public void pickImageFromCamera() {
//        if (!PermissionCaller.getInstance(this).isListOfPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.CAMERA}, REQUEST_CAPTURE))
//            return;
//        Intent intent = new Intent();
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//        try {
//            File tempFile = File.createTempFile("image", ".png", new File(Utility.getTempMediaDirectory(this)));
////            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
////            log("storageDir: " + storageDir);
////            File tempFile = File.createTempFile("image", ".png", storageDir);
//            log("tempFile: " + tempFile);
//            log("tempFile uri: " + Uri.fromFile(tempFile));
//            captureMediaFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", tempFile);
////            captureMediaFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, tempFile);
//            log("captureMediaFile: " + captureMediaFile);
////            File file = getOutputMediaFile(1);
////            captureMediaFile = Uri.fromFile(file); // create
//
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
//
//            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            for (ResolveInfo resolveInfo : resInfoList) {
//                String packageName = resolveInfo.activityInfo.packageName;
//                grantUriPermission(packageName, captureMediaFile, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        startActivityForResult(intent, REQUEST_CAPTURE);
//    }



    /* Check whether this uri is a content uri or not.
     *  content uri like content://media/external/images/media/1302716
     *  */
    private boolean isContentUri(Uri uri) {
        boolean ret = false;
        if (uri != null) {
            String uriSchema = uri.getScheme();
            if ("content".equalsIgnoreCase(uriSchema)) {
                ret = true;
            }
        }
        return ret;
    }

    /* Check whether this uri is a file uri or not.
     *  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
     * */
    private boolean isFileUri(Uri uri) {
        boolean ret = false;
        if (uri != null) {
            String uriSchema = uri.getScheme();
            if ("file".equalsIgnoreCase(uriSchema)) {
                ret = true;
            }
        }
        return ret;
    }


    /* Check whether this document is provided by ExternalStorageProvider. */
    private boolean isExternalStoreDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.android.externalstorage.documents".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by DownloadsProvider. */
    private boolean isDownloadDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.android.providers.downloads.documents".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by MediaProvider. */
    private boolean isMediaDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.android.providers.media.documents".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by google photos. */
    private boolean isGooglePhotoDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.google.android.apps.photos.content".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Return uri represented document file real local path.*/
    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause) {
        String ret = "";

        // Query the uri with condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);

        if (cursor != null) {
            boolean moveToFirst = cursor.moveToFirst();
            if (moveToFirst) {

                // Get columns name by uri type.
                String columnName = MediaStore.Images.Media.DATA;

                if (uri == MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Images.Media.DATA;
                } else if (uri == MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Audio.Media.DATA;
                } else if (uri == MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Video.Media.DATA;
                }

                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);

                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }
        }

        return ret;
    }


    /**
     * Create a File for saving an image
     */
//    private File getOutputMediaFile(int type) {
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), AppConstants.SAVE_IMAGE_DIRECTORY);
//
//        /**Create the storage directory if it does not exist*/
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                return null;
//            }
//        }
//
//        /**Create a media file name*/
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile;
//        if (type == 1) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                    "IMG_" + timeStamp + ".png");
//        } else {
//            return null;
//        }
//
//        return mediaFile;
//    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "BookComplaint", null);

        return Uri.parse(path);

    }

    public String getRealPathFromURI(Uri uri) {



        String mCurrentPhotoPath = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

            mCurrentPhotoPath = cursor.getString(idx);
            cursor.close();
        }

        return mCurrentPhotoPath;

    }


    public AlertDialog.Builder getAlertDialogBuilder(String title, String message, boolean cancellable) {
        return new AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancellable);
    }


    public void openExternalWebView(String url) {

        if (url != null) {
            Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent1);
        }
    }


    public String tag() {
        return getClass().getSimpleName();
    }

    public void log(String message) {
        Log.d(tag(), message);
    }

    public void loadProgressBar(Context context, String message, boolean cancellable) {

        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setMessage(message);
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                pDialog.setIndeterminateDrawable(getDrawable(R.drawable.progress_dilog));
            }
//            pDialog= new ProgressDialog(context, R.style.MyAlertDialogStyle);

            pDialog.setCancelable(false);
            //            pDialog.setIndeterminate(true);
        }
        if (!pDialog.isShowing())
            pDialog.show();

    }

    public void dismissProgressBar(Context context) {

        if (pDialog != null) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            pDialog = null;
        }
    }

//    public AlertDialog.Builder getAlertDialogBuilder(String title, String message, boolean cancellable) {
//        return new AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(cancellable);
//    }



    public void enableLoadingBar(Context context, boolean enable, String s) {
        if (enable) {
            loadProgressBar(context, s, false);
        } else {
            dismissProgressBar(context);
        }
    }

    public void onError(String reason) {
        onError(reason, false);
    }

    public void onError(String reason, final boolean finishOnOk) {
        if (reason != null && !reason.isEmpty()) {
//            getAlertDialogBuilder(null, reason, true).setPositiveButton(getString(R.string.btn_ok), finishOnOk ? new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
//                        }
//                    } : null).show();
        } else {
//            getAlertDialogBuilder(null, getString(R.string.default_error), true)
//                    .setPositiveButton(getString(R.string.btn_ok), finishOnOk ? new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
//                        }
//                    } : null).show();
        }
    }







//    public void onError(String reason, final boolean finishOnOk) {
//        if (reason != null && !reason.isEmpty()) {
//            getAlertDialogBuilder(null, reason, false)
//                    .setPositiveButton(getString(com.b2c.audience.R.string.btn_ok), finishOnOk ? new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
//                        }
//                    } : null).show();
//        } else {
//            getAlertDialogBuilder(null, getString(com.b2c.audience.R.string.default_error), false)
//                    .setPositiveButton(getString(com.b2c.audience.R.string.btn_ok), finishOnOk ? new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
//                        }
//                    } : null).show();
//        }
//    }

//    public void UserLogout() {
//
//        SessionManager.clearAll(getApplicationContext());
//
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finishAffinity();
//    }
//    public void dialogAccountDeactivate(String msg) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.warning));
//        builder.setMessage(msg);
//        builder.setCancelable(true);
//        builder.setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//                UserLogout();
//            }
//        });
//
//        final AlertDialog alert = builder.create();
//        //2. now setup to change color of the button
//        alert.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alertDialogButton));
//            }
//        });
//        alert.show();
//    }


}
