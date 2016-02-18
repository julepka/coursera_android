package com.example.dailyphoto;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.dailyphoto.oath.EasyHttpClient;
import com.example.dailyphoto.oath.SecuredRestBuilder;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;

public class PhotoServiceProvider {

	static final String path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
			"MyCameraApp").getAbsolutePath();

	private PhotoServiceApi photoServiceApi;
	public Collection<Photo> photoList;
	final private Context context;

	public static final String CLIENT_ID = "mobile";

	public PhotoServiceProvider(Context c) {
		context = c;
	}

	public synchronized void login(String server, String username, String password) {
		photoServiceApi = new SecuredRestBuilder().setLoginEndpoint(server + PhotoServiceApi.TOKEN_PATH)
				.setUsername(username).setPassword(password).setClientId(CLIENT_ID)
				.setClient(new ApacheClient(new EasyHttpClient())).setEndpoint(server).setLogLevel(LogLevel.FULL)
				.build().create(PhotoServiceApi.class);
	}

	public synchronized void getPhotoList() {
		new GetPhotoListTask().execute();
	}

	public void uploadPhoto(Photo photo) {
		new UploadPhotoTask().execute(photo);
	}

	public synchronized boolean downloadMissingFiles() {
		for (Photo photo : photoList) {
			Log.d("test", "downloading: " + photo.getUrl());
			if (!(new File(photo.getUrl())).exists()) {
				new DownloadFileTask().execute(photo.getId());
				return true;
			}
		}
		return false;
	}

	private static class FilterParams {
		ArrayList<Integer> filterIds;
		Long photoId;

		FilterParams(ArrayList<Integer> filters, Long photo) {
			this.filterIds = filters;
			this.photoId = photo;
		}
	}

	public void filterPhotos(ArrayList<Long> photoIds, ArrayList<Integer> filterIds) {
		for (long photo : photoIds) {
			new FilterPhotoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new FilterParams(filterIds, photo));
		}
	}

	// -------------------- ASYNCTASKS --------------------

	private class GetPhotoListTask extends AsyncTask<Void, Void, Collection<Photo>> {
		@Override
		protected Collection<Photo> doInBackground(Void... params) {
			Collection<Photo> result = null;
			try {
				result = photoServiceApi.getPhotoList();
			} catch (Exception e) {
				Log.e("GetPhotoListTask", e.toString());
			}
			return result;
		}

		protected void onPostExecute(Collection<Photo> result) {
			if (result == null) {
				((MainActivity) context).showToast("Incorrect username or password");
			} else {
				Log.d("test", "assigning photo list");
				photoList = result;
				((MainActivity) context).openPhotoListFragment();
			}
		}
	}

	private class UploadPhotoTask extends AsyncTask<Photo, Void, Photo> {
		@Override
		protected Photo doInBackground(Photo... params) {
			try {
				Photo addedPhoto = photoServiceApi.addPhoto(params[0]);
				Log.d("test", "addPhoto()");
				return addedPhoto;
			} catch (Exception e) {
				Log.e("UploadPhotoTask", e.toString());
			}
			return null;
		}

		protected void onPostExecute(Photo param) {
			Log.d("test", "onPostExecute after upload photo");
			getPhotoList();
			if (param != null)
				new UploadFileTask().execute(param);
		}
	}

	private class UploadFileTask extends AsyncTask<Photo, Void, Void> {
		@Override
		protected Void doInBackground(Photo... params) {
			Log.d("test", params[0].getUrl());
			TypedFile typedFile = new TypedFile("image/*", new File(params[0].getUrl()));
			try {
				photoServiceApi.addPhotoFile(typedFile, params[0].getId());
			} catch (Exception e) {
				Log.e("UploadFileTask", e.toString());
			}
			return null;
		}

	}

	private class DownloadFileTask extends AsyncTask<Long, Void, Void> {
		@Override
		protected Void doInBackground(Long... params) {
			Log.d("test", "started Download File Task");
			TypedInput body = photoServiceApi.getPhotoFileById(params[0]).getBody();
			saveFile(body, params[0]);
			return null;
		}

		protected void onPostExecute(Void params) {
			((MainActivity) context).updateGrid();
		}
	}

	private void saveFile(TypedInput body, Long id) {
		if (body instanceof TypedByteArray) {
			Log.i("DownloadFileTask", "received body which is TypedByteArray: " + body);
		} else {
			Log.e("DownloadFileTask", "[!] received body which is NOT a TypedByteArray: " + body);
			return;
		}
		TypedByteArray byteArrayRaw = (TypedByteArray) body;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream((int) byteArrayRaw.length());
		try {
			IOUtils.copy(byteArrayRaw.in(), buffer);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Log.e("DownloadFileTask", "TypedByteArray byte count: " + buffer.toByteArray().length);
		String localPath = "";
		try {
			localPath = photoServiceApi.getPhotoById(id).getUrl();
		} catch (Exception e) {
			Log.e("saveFile", e.toString());
		}
		try {
			DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(localPath, false));
			try {
				outputStream.write(buffer.toByteArray());
			} finally {
				outputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.e("DownloadFileTask", "Failed to write local file: " + e.getMessage());
			e.printStackTrace();
			return;
		} catch (IOException e) {
			Log.e("DownloadFileTask", "Failed to write local file: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		Log.d("test", "123h");
	}

	private class FilterPhotoTask extends AsyncTask<FilterParams, Void, Boolean> {
		@Override
		protected Boolean doInBackground(FilterParams... params) {
			Long photoId = params[0].photoId;
			ArrayList<Integer> filters = params[0].filterIds;
			int grayscale = 0, sepia = 0, invertColors = 0, brighten = 0, darken = 0, blur = 0, sharpen = 0;
			for (Integer filter : filters) {
				if (filter == 0)
					grayscale = 1;
				if (filter == 1)
					sepia = 1;
				if (filter == 2)
					invertColors = 1;
				if (filter == 3)
					brighten = 1;
				if (filter == 4)
					darken = 1;
				if (filter == 5)
					blur = 1;
				if (filter == 6)
					sharpen = 1;
			}
			TypedInput fileBody = null;
			try {
				fileBody = photoServiceApi.filterPhoto(photoId, grayscale, sepia, invertColors, brighten, darken, blur,
						sharpen).getBody();
			} catch (Exception e) {
				Log.e("FilterPhotoTask", e.toString());
			}

			if (fileBody != null) {
				Log.d("test", "response file is not null");
				saveFile(fileBody, photoId);
				return true;
			}
			return false;
		}

		protected void onPostExecute(Boolean fileNotNull) {
			if (fileNotNull) {
				((MainActivity) context).updateGrid();
			}
		}

	}
}
