package com.example.dailyphoto.oath;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.io.IOUtils;

import retrofit.Endpoint;
import retrofit.ErrorHandler;
import retrofit.Profiler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Log;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;
import retrofit.client.Client.Provider;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.mime.FormUrlEncodedTypedOutput;

import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SecuredRestBuilder extends RestAdapter.Builder {

	private class OAuthHandler implements RequestInterceptor {

		private boolean loggedIn;
		private Client client;
		private String tokenIssuingEndpoint;
		private String username;
		private String password;
		private String clientId;
		private String clientSecret;
		private String accessToken;

		public OAuthHandler(Client client, String tokenIssuingEndpoint, String username,
				String password, String clientId, String clientSecret) {
			super();
			this.client = client;
			this.tokenIssuingEndpoint = tokenIssuingEndpoint;
			this.username = username;
			this.password = password;
			this.clientId = clientId;
			this.clientSecret = clientSecret;
		}

		@Override
		public void intercept(RequestFacade request) {
			if (!loggedIn) {
				try {
					FormUrlEncodedTypedOutput to = new FormUrlEncodedTypedOutput();
					to.addField("username", username);
					to.addField("password", password);
					to.addField("client_id", clientId);
					to.addField("client_secret", clientSecret);
					to.addField("grant_type", "password");
					String base64Auth = BaseEncoding.base64().encode(new String(clientId + ":" + clientSecret).getBytes());
					List<Header> headers = new ArrayList<Header>();
					headers.add(new Header("Authorization", "Basic " + base64Auth));
					Request req = new Request("POST", tokenIssuingEndpoint, headers, to);
					Response resp = client.execute(req);
					if (resp.getStatus() < 200 || resp.getStatus() > 299) {
						throw new SecuredRestException("Login failure: "
								+ resp.getStatus() + " - " + resp.getReason());
					} else {
				        String body = IOUtils.toString(resp.getBody().in());
						accessToken = new Gson().fromJson(body, JsonObject.class).get("access_token").getAsString();
						request.addHeader("Authorization", "Bearer " + accessToken);	
						loggedIn = true;
					}
				} catch (Exception e) {
					throw new SecuredRestException(e);
				}
			}
			else {
				request.addHeader("Authorization", "Bearer " + accessToken );
			}
		}

	}

	private String username;
	private String password;
	private String loginUrl;
	private String clientId;
	private String clientSecret = "";
	private Client client;
	
	public SecuredRestBuilder setLoginEndpoint(String endpoint){
		loginUrl = endpoint;
		return this;
	}

	@Override
	public SecuredRestBuilder setEndpoint(String endpoint) {
		return (SecuredRestBuilder) super.setEndpoint(endpoint);
	}

	@Override
	public SecuredRestBuilder setEndpoint(Endpoint endpoint) {
		return (SecuredRestBuilder) super.setEndpoint(endpoint);
	}

	@Override
	public SecuredRestBuilder setClient(Client client) {
		this.client = client;
		return (SecuredRestBuilder) super.setClient(client);
	}

	@Override
	public SecuredRestBuilder setClient(Provider clientProvider) {
		client = clientProvider.get();
		return (SecuredRestBuilder) super.setClient(clientProvider);
	}

	@Override
	public SecuredRestBuilder setErrorHandler(ErrorHandler errorHandler) {

		return (SecuredRestBuilder) super.setErrorHandler(errorHandler);
	}

	@Override
	public SecuredRestBuilder setExecutors(Executor httpExecutor,
			Executor callbackExecutor) {

		return (SecuredRestBuilder) super.setExecutors(httpExecutor,
				callbackExecutor);
	}

	@Override
	public SecuredRestBuilder setRequestInterceptor(
			RequestInterceptor requestInterceptor) {

		return (SecuredRestBuilder) super
				.setRequestInterceptor(requestInterceptor);
	}

	@Override
	public SecuredRestBuilder setConverter(Converter converter) {

		return (SecuredRestBuilder) super.setConverter(converter);
	}

	@Override
	public SecuredRestBuilder setProfiler(@SuppressWarnings("rawtypes") Profiler profiler) {

		return (SecuredRestBuilder) super.setProfiler(profiler);
	}

	@Override
	public SecuredRestBuilder setLog(Log log) {

		return (SecuredRestBuilder) super.setLog(log);
	}

	@Override
	public SecuredRestBuilder setLogLevel(LogLevel logLevel) {

		return (SecuredRestBuilder) super.setLogLevel(logLevel);
	}

	public SecuredRestBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	public SecuredRestBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public SecuredRestBuilder setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	
	public SecuredRestBuilder setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
	
		

	@Override
	public RestAdapter build() {
		if (username == null || password == null) {
			throw new SecuredRestException(
					"You must specify both a username and password for a "
							+ "SecuredRestBuilder before calling the build() method.");
		}

		if (client == null) {
			client = new OkClient();
		}
		OAuthHandler hdlr = new OAuthHandler(client, loginUrl, username, password, clientId, clientSecret);
		setRequestInterceptor(hdlr);

		return super.build();
	}
}