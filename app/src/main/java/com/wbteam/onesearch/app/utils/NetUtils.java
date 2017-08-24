package com.wbteam.onesearch.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.wbteam.onesearch.app.AppContext;

/**
 *  @author 码农哥
 *	@date 2016-3-28下午3:41:47
 *	@email 441293364@qq.com
 *	@TODO
 *
 *  ** *** ━━━━━━神兽出没━━━━━━
 *  ** ***       ┏┓　　  ┏┓
 *  ** *** 	   ┏┛┻━━━┛┻┓
 *  ** *** 　  ┃　　　　　　　┃
 *  ** *** 　　┃　　　━　　　┃
 *  ** *** 　　┃　┳┛　┗┳　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┃　　　┻　　　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┗━┓　　　┏━┛
 *  ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 *  ** *** 　　　　┃　　　┃
 *  ** *** 　　　　┃　　　┗━━━┓
 *  ** *** 　　　　┃　　　　　　　┣┓
 *  ** *** 　　　　┃　　　　　　　┏┛
 *  ** *** 　　　　┗┓┓┏━┳┓┏┛
 *  ** *** 　　　　  ┃┫┫  ┃┫┫
 *  ** *** 　　　　  ┗┻┛　┗┻┛
 */

@SuppressLint("NewApi") 
public class NetUtils {
	/**
	 * 检查是否连接了Wifi
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isWifiConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isWifiConnected = false;
		NetworkInfo networkInfo = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Network[] networks = connMgr.getAllNetworks();
			for (Network network : networks) {
				networkInfo = connMgr.getNetworkInfo(network);
				if (!isWifiConnected && ConnectivityManager.TYPE_WIFI == networkInfo.getType() && networkInfo.isConnected()) {
					isWifiConnected = true;
					break;
				}
			}
		} else {
			networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null && networkInfo.isConnected()) {
				isWifiConnected = true;
			}
		}
		return isWifiConnected;
	}

	/**
	 * 检查是否连接了手机网络
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isMobileConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isMobileConnected = false;
		NetworkInfo networkInfo = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Network[] networks = connMgr.getAllNetworks();
			for (Network network : networks) {
				networkInfo = connMgr.getNetworkInfo(network);
				if (!isMobileConnected && ConnectivityManager.TYPE_MOBILE == networkInfo.getType() && networkInfo.isConnected()) {
					isMobileConnected = true;
					break;
				}
			}
		} else {
			networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (networkInfo != null && networkInfo.isConnected()) {
				isMobileConnected = true;
			}
		}
		return isMobileConnected;
	}

	/**
	 * 检查是否在线（Wifi或者手机网络）
	 * 
	 * @return
	 */
	public static boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) AppContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	/**
	 * 强制 HttpsURLConnection 信任任何证书，该操作很危险，仅用于测试，建议不要在生产环境使用
	 */
	@SuppressLint("TrulyRandom") @Deprecated
	public static SSLSocketFactory getTrustedAnyCertsSocketFactory() {

		try {

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					X509Certificate[] myTrustedAnchors = new X509Certificate[0];
					return myTrustedAnchors;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			sc.getSocketFactory();
		} catch (Exception e) {
		}

		return null;

	}

	/**
	 * 加载可信的证书文件，并返回SSLSocketFactory
	 * 
	 * @param certificates
	 *            ，证书，可以放在assets下面，getAssets().open("***.cert");
	 * @return 加载了证书的 SSLSocketFactory
	 */
	public static SSLSocketFactory loadTrustedCertificates(InputStream... certificates) {
		try {

			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null);
			int index = 0;
			for (InputStream certificate : certificates) {

				String certificateAlias = Integer.toString(index++);
				keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

				try {
					if (certificate != null) {
						certificate.close();
					}
				} catch (IOException e) {
					Log.e("SSLSocketFactory", "Load & Close Certificates Error!");
				}

			}

			SSLContext sslContext = SSLContext.getInstance("TLS");

			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			return sslContext.getSocketFactory();

		} catch (Exception e) {

			Log.e("SSLSocketFactory", "CertificateException or KeyStoreException");
		}

		return null;
	}
}
