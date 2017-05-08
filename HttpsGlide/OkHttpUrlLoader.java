package com.bignerdranch.android.photogallery.Utils.HttpsGlide;

import android.content.Context;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import okhttp3.OkHttpClient;

import java.io.InputStream;

public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
	public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
		private OkHttpClient client;

		public Factory(OkHttpClient client) {
			this.client = client;
		}

		@Override
		public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
			return new OkHttpUrlLoader(client);
		}

		@Override
		public void teardown() { }
	}

	private final OkHttpClient client;

	public OkHttpUrlLoader(OkHttpClient client) {
		this.client = client;
	}

	@Override
	public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
		return new OkHttpStreamFetcher(client, model);
	}
}
