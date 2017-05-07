package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.support.annotation.RawRes;
import okhttp3.OkHttpClient;
import okio.Buffer;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

public class HttpsOkHttpClient {
	public static OkHttpClient Create(Context context, String publicKey, HostnameVerifier hostnameVerifier) {
		InputStream publicKeyStream = new Buffer().writeUtf8(publicKey).inputStream();
		return HttpsOkHttpClient.Create(context, publicKey, hostnameVerifier);
	}

	public static OkHttpClient Create(Context context, InputStream publicKeyStream, HostnameVerifier hostnameVerifier) {
		KeyStore keyStore = null;
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			Certificate certificate = certificateFactory.generateCertificate(publicKeyStream);
			String keyStoreType = KeyStore.getDefaultType();
			keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("certificate", certificate);
		} catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpsOkHttpClient.CreateIml(context, keyStore, hostnameVerifier);
	}

	public static OkHttpClient Create(Context context, @RawRes int keyStoreFileId, HostnameVerifier hostnameVerifier) {
		return HttpsOkHttpClient.Create(context, keyStoreFileId, "", hostnameVerifier);
	}

	public static OkHttpClient Create(Context context, @RawRes int keyStoreFileId, String password, HostnameVerifier hostnameVerifier) {
		KeyStore keyStore = null;
		try {
			String keyStoreType = KeyStore.getDefaultType();
			keyStore = KeyStore.getInstance(keyStoreType);
			InputStream fileIS = context.getApplicationContext().getResources().openRawResource(keyStoreFileId);
			keyStore.load(fileIS, password.toCharArray());
			// Todo: 文件流异常问题
			if (fileIS != null) fileIS.close();
		} catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return HttpsOkHttpClient.CreateIml(context, keyStore, hostnameVerifier);
	}

	private static OkHttpClient CreateIml(Context context, KeyStore keyStore, HostnameVerifier hostnameVerifier) {
		SSLSocketFactory sslSocketFactory = null;
		X509TrustManager trustManager = null;

		try {
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory trustManagerFactory;
			trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
			trustManagerFactory.init(keyStore);
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

			String kmAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(kmAlgorithm);
			keyManagerFactory.init(keyStore, "Password".toCharArray());
			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyManagers, trustManagers, new SecureRandom());

			sslSocketFactory = sslContext.getSocketFactory();

			if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
				throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
			}

			trustManager = (X509TrustManager) trustManagers[0];
		} catch (NoSuchAlgorithmException | KeyManagementException | UnrecoverableKeyException | KeyStoreException e) {
			e.printStackTrace();
		}

		assert sslSocketFactory != null;
		return new OkHttpClient.Builder()
				.sslSocketFactory(sslSocketFactory, trustManager)
				.hostnameVerifier(hostnameVerifier)
				.build();
	}
}
