package com.fanqi.succulent.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaverUtil {

    private static final String SAVE_DIR_NAME = "SucculentsImages";

    public static final void saveImage(Context context, ImageView imageView) {
//       Bitmap bitmap= new Bitmap(imageView.getDrawable());
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        String path = Environment.getExternalStorageDirectory() + "/" + SAVE_DIR_NAME;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        String imageName = System.currentTimeMillis() + ".jpg";
        file = new File(path + "/" + imageName);
//        Uri imageUri = FileProvider.getUriForFile(context,
//                context.getApplicationContext().getPackageName() + ".fileprovider", file);
////        file = new File(imageUri.getEncodedPath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ClosableCloser.close(fos);
        }
        Toast.makeText(context, "文件已保存在\n" + path + "/" + imageName, Toast.LENGTH_SHORT).show();
    }
}
