package com.example.dailyphoto;

import retrofit.client.Response;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.Collection;

public interface PhotoServiceApi {

	public static final String PASSWORD_PARAMETER = "password";

	public static final String USERNAME_PARAMETER = "username";

	public static final String ID_PARAMETER = "id";

	public static final String TOKEN_PATH = "/oauth/token";

	public static final String PHOTO_SERVICE_PATH = "/photo";

	public static final String PHOTO_ID_SEARCH_PATH = PHOTO_SERVICE_PATH + "/{id}";

	public static final String PHOTO_FILE_SEARCH_PATH = PHOTO_SERVICE_PATH + "/{id}/file";

	public static final String PHOTO_FILE_UPLOAD_PATH = PHOTO_SERVICE_PATH + "/upload/{id}";

	@GET(PHOTO_SERVICE_PATH)
	public Collection<Photo> getPhotoList();

	@POST(PHOTO_SERVICE_PATH)
	public Photo addPhoto(@Body Photo v);

	@Multipart
	@POST(PHOTO_FILE_UPLOAD_PATH)
	public Long addPhotoFile(@Part(value = "file") TypedFile file, @Path(value = "id") long id);

	@GET(PHOTO_ID_SEARCH_PATH)
	public Photo getPhotoById(@Path(ID_PARAMETER) long id);

	@Streaming
	@GET(PHOTO_FILE_SEARCH_PATH)
	public Response getPhotoFileById(@Path(ID_PARAMETER) long id);
	
	@Streaming
	@GET(PHOTO_SERVICE_PATH + "/filter/{id}/{grayScale}/{sepia}/{invertColors}/{brighten}/{darken}/{blur}/{sharpen}")
	public Response filterPhoto(@Path("id") long id,
								@Path("grayScale") int grayScale,
								@Path("sepia") int sepia,
								@Path("invertColors") int invertColors,
								@Path("brighten") int brighten,
								@Path("darken") int darken,
								@Path("blur") int blur,
								@Path("sharpen") int sharpen);

}