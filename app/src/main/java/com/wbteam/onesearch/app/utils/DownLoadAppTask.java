package com.wbteam.onesearch.app.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.wbteam.onesearch.R;

/**
 *  
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-11-9  下午4:09:18
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class DownLoadAppTask extends AsyncTask<String, Void, Object> {
	public Context context;
	private File file;

	public DownLoadAppTask(Context context) {
		this.context = context;
	}

	@Override
	protected Object doInBackground(String... params) {
		long totalSize = 0;
		int updateTotalSize = 0;
		int downloadCount = 0;
		File tmpFile = new File(PathUtils.getDownloadPath());
		if (!tmpFile.exists()) {
			tmpFile.mkdirs();
		}
		
		file = new File(PathUtils.getDownloadPath() + getApkName(params[0]));
		
		Logger.e("TAG", "文件路径："+(PathUtils.getDownloadPath() + getApkName(params[0])));
		
		if (file.exists())
			file.delete();

		try {
			URL url = new URL(params[0]);
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				InputStream is = conn.getInputStream();
				updateTotalSize = conn.getContentLength();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				conn.connect();
				double count = 0;
				if (conn.getResponseCode() >= 400) {
					Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
				} else {
					while (count <= 100) {
						if (is != null) {
							int numRead = is.read(buf);
							if (numRead <= 0) {
								break;
							} else {
								fos.write(buf, 0, numRead);
								totalSize += numRead;
								int tmp = (int) (totalSize * 100 / updateTotalSize);
								if (downloadCount == 0 || tmp - 1 > downloadCount) {
									downloadCount += 1;
									Message msg = handler.obtainMessage();
									msg.what = 0;
									msg.arg1 = downloadCount;
									handler.sendMessage(msg);
								}
							}

						} else {
							break;
						}

					}
				}
				conn.disconnect();
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return file;
	}

	@Override
	protected void onPreExecute() {
		NotificationManages(context.getString(R.string.app_name));
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Object result) {
		Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
		handler.sendEmptyMessage(10010);
		openFile((File) result);
		super.onPostExecute(result);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.coobar.webmaster.app.ACTION_DOWNLOAD")) {
				if(isDownLoadFinish)
					openFile(file);
				else{
					TipsToast.showToast(context, "文件正在下载中！");
				}
			}
		}

	};
	
	/**
	 * 打开安装文件
	 * 
	 * @param file
	 */
	private void openFile(File file) {
		if(file.exists()){
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			context.startActivity(intent);
		}else{
			TipsToast.showToast(context, "文件操作错误");
		}
	}
	
	private boolean isDownLoadFinish = false;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				isDownLoadFinish = false;
				notification.contentView.setProgressBar(R.id.notify_app_progress, 100, msg.arg1, false);
				notification.contentView.setTextViewText(R.id.notify_progress_values, context.getString(R.string.app_name) + "下载进度\t:" + msg.arg1 + "%");
				Logger.d("TAG", "进度：" + msg.arg1);
				manager.notify(0, notification);
				break;

			case 10010:
				isDownLoadFinish = true;
				notification.contentView.setProgressBar(R.id.notify_app_progress, 100, 100, false);
				notification.contentView.setViewVisibility(R.id.notify_app_progress, View.GONE);
				notification.contentView.setTextViewText(R.id.notify_progress_values, "下载已完成");
				manager.notify(0, notification);
				IntentFilter filter = new IntentFilter();
				filter.addAction("com.coobar.webmaster.app.ACTION_DOWNLOAD");
				context.registerReceiver(receiver, filter);
				break;
			}
		};
	};

	Notification notification;
	NotificationManager manager;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");

	private void NotificationManages(String str) {
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		Intent intent = new Intent("com.coobar.webmaster.app.ACTION_DOWNLOAD");
		PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		notification.tickerText = "开始下载【" + str + "】";
		notification.contentView = new RemoteViews(context.getPackageName(), R.layout.view_download_layout);
		notification.icon = R.drawable.app_icon;
		notification.contentView.setImageViewResource(R.id.notify_app_icon, R.drawable.app_icon);
		notification.contentView.setProgressBar(R.id.notify_app_progress, 100, 0, false);
		notification.contentView.setTextViewText(R.id.notify_app_time, format.format(new Date()));
		notification.contentIntent = pIntent;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		manager.notify(0, notification);
	}

	private String getApkName(String name) {
		String result = "";
		String names[] = name.split("/");

		for (int i = 0; i < names.length; i++) {
			String string = names[i].toString();
			if (string.endsWith(".apk")) {
				result = string;
			}
		}
		return result;
	}
}
