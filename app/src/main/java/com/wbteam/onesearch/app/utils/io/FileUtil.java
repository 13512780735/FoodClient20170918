package com.wbteam.onesearch.app.utils.io;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

@SuppressLint("NewApi") public class FileUtil {

    public static boolean checkSDcard() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static void saveFileCache(byte[] fileData, String folderPath, String fileName) {
        File folder = new File(folderPath);
        folder.mkdirs();
        File file = new File(folderPath, fileName);
        ByteArrayInputStream is = new ByteArrayInputStream(fileData);
        FileOutputStream os = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
                os = new FileOutputStream(file);
                byte[] e = new byte[1024];
                int len1;
                while (-1 != (len1 = is.read(e))) {
                    os.write(e, 0, len1);
                }

                os.flush();
            } catch (Exception var12) {
                throw new RuntimeException(FileUtil.class.getClass().getName(), var12);
            } finally {
                closeIO(is, os);
            }
        }

    }

    public static File getSaveFile(String folderPath, String fileNmae) {
        File file = new File(getSavePath(folderPath) + File.separator + fileNmae);

        try {
            file.createNewFile();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return file;
    }

    public static String getSavePath(String folderName) {
        return getSaveFolder(folderName).getAbsolutePath();
    }

    public static File getSaveFolder(String folderName) {
        File file = new File(getSDCardPath() + File.separator + folderName + File.separator);
        file.mkdirs();
        return file;
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static final byte[] input2byte(InputStream inStream) {
        if (inStream == null) {
            return null;
        } else {
            byte[] in2b = null;
            BufferedInputStream in = new BufferedInputStream(inStream);
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            try {
                int rc1;
                while ((rc1 = in.read()) != -1) {
                    swapStream.write(rc1);
                }

                in2b = swapStream.toByteArray();
            } catch (IOException var9) {
                var9.printStackTrace();
            } finally {
                closeIO(inStream, in, swapStream);
            }

            return in2b;
        }
    }

    public static File uri2File(Activity aty, Uri uri) {
        String[] projection;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            projection = new String[]{"_data"};
            @SuppressWarnings("deprecation")
			Cursor loader1 = aty.managedQuery(uri, projection, null, null, null);
            int cursor1 = loader1.getColumnIndexOrThrow("_data");
            loader1.moveToFirst();
            String column_index1 = loader1.getString(cursor1);
            return new File(column_index1);
        } else {
            projection = new String[]{"_data"};
            CursorLoader loader = new CursorLoader(aty, uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            return new File(cursor.getString(column_index));
        }
    }

    public static void copyFile(File from, File to) {
        if (null != from && from.exists()) {
            if (null != to) {
                FileInputStream is = null;
                FileOutputStream os = null;

                try {
                    is = new FileInputStream(from);
                    if (!to.exists()) {
                        to.createNewFile();
                    }

                    os = new FileOutputStream(to);
                    copyFileFast(is, os);
                } catch (Exception var8) {
                    throw new RuntimeException(FileUtil.class.getClass().getName(), var8);
                } finally {
                    closeIO(is, os);
                }

            }
        }
    }

    public static void copyFileFast(FileInputStream is, FileOutputStream os) throws IOException {
        FileChannel in = is.getChannel();
        FileChannel out = os.getChannel();
        in.transferTo(0L, in.size(), out);
    }

    public static void closeIO(Closeable... closeables) {
        if (null != closeables && closeables.length > 0) {
            Closeable[] var1 = closeables;
            int var2 = closeables.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Closeable cb = var1[var3];

                try {
                    if (null != cb) {
                        cb.close();
                    }
                } catch (IOException var6) {
                    throw new RuntimeException(FileUtil.class.getClass().getName(), var6);
                }
            }

        }
    }

    public static boolean bitmapToFile(Bitmap bitmap, String filePath) {
        boolean isSuccess = false;
        if (bitmap == null) {
            return isSuccess;
        } else {
            File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }

            BufferedOutputStream out = null;

            try {
                out = new BufferedOutputStream(new FileOutputStream(filePath), 8192);
                isSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (FileNotFoundException var9) {
                var9.printStackTrace();
            } finally {
                closeIO(out);
            }

            return isSuccess;
        }
    }

    public static String readFile(String filePath) {
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);
        } catch (Exception var3) {
            throw new RuntimeException(FileUtil.class.getName() + "readFile---->" + filePath + " not found");
        }

        return inputStream2String(is);
    }

    public static String readFileFromAssets(Context context, String name) {
        InputStream is = null;

        try {
            is = context.getResources().getAssets().open(name);
        } catch (Exception var4) {
            throw new RuntimeException(FileUtil.class.getName() + ".readFileFromAssets---->" + name + " not found");
        }

        return inputStream2String(is);
    }

    public static String inputStream2String(InputStream is) {
        if (null == is) {
            return null;
        } else {
            StringBuilder resultSb = null;

            try {
                BufferedReader ex = new BufferedReader(new InputStreamReader(is));
                resultSb = new StringBuilder();

                String len;
                while (null != (len = ex.readLine())) {
                    resultSb.append(len);
                }
            } catch (Exception var7) {
            } finally {
                closeIO(is);
            }

            return null == resultSb ? null : resultSb.toString();
        }
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    public static void writeStringToFile(File file, String data, String encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }

    public static void writeStringToFile(File file, String data, String encoding, boolean append) throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file, append);
            IOUtils.write(data, out, encoding);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     *
     * @param file
     * @param data
     * @throws IOException
     */
    public static void writeStringToFile(File file, String data) throws IOException {
        writeStringToFile(file, data, null, false);
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    @SuppressWarnings("unused")
	public static void delete(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                for (File f: file.listFiles()) {
                    delete(file);
                }
            }else{
                file.delete();
            }
        }
    }

    /**
     * 读取本地图片成bitmap
     * @return
     */
    public static Bitmap getDistImage(String pathNmae){
        Bitmap bitmap = null;
        try{
            File file = new File(pathNmae);
            if(file.exists()){
                bitmap = BitmapFactory.decodeFile(pathNmae);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}


