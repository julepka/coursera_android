# Daily Photo Capstone Project
## Mobile Cloud Computing with Android Specialization

Mobile Cloud Computing with Android Specialization is a series of Android courses offered by Coursera. It is presented by University of Maryland and Vanderbilt University. The instructors are: Dr. Adam Porter, Dr. Douglas C. Schmidt, Dr. C. Jules White. Specialization consists of seven parts:
* Programming Mobile Applications for Android Handheld Systems: Part 1;
* Programming Mobile Applications for Android Handheld Systems: Part 2;
* Programming Mobile Services for Android Handheld Systems: Concurrency;
* Programming Mobile Services for Android Handheld Systems: Communication;
* Programming Cloud Services for Android Handheld Systems: Spring;
* Programming Cloud Services for Android Handheld Systems: Security;
* The Android Capstone Project.

Capstone Project shows what students have learned during Specialization. Students need to pass all courses with good grades to be able to take part in Capstone Project.

## Project Requirements

I worked on The Daily Selfie with Concurrent Image Processing Capstone Project. The Concurrent Daily Selfie application is an extension of one of the mini-projects in Professor Porter’s MOOCs, which enables users to take pictures of themselves - selfies - over an extended period of time. It periodically reminds the user to take a selfie and presents the selfies in a list that makes it easy to see how the user has changed over time. This extended implementation also allows users to process their selfies to add effects, such as blurring or charcoaling. The image processing is done via a remote web service. In addition, all interactions between the device and the remote web service should be done concurrently in the background to ensure that the UI thread on the device is not interrupted. 

### Basic Project Requirements

The Capstone project must support multiple users and should leverage one or more services running remotely in the cloud. The  project's specification clearly outlines the app's intended high-level behavior, yet leaves substantial room for individual creativity. Students will therefore need to flesh out many important design and implementation details.  Basic requirements for all Capstone MOOC project specifications include:
* Apps must support multiple users via individual user accounts. 
* At least one user facing operation must be available only to authenticated users.
* App implementations must comprise at least one instance of at least two of the following four fundamental Android components: Activity, BroadcastReceiver, Service, and ContentProvider.
* Apps must interact with at least one remotely-hosted Java Spring-based service over the network via HTTP/HTTPS.
* At runtime apps must allow users to navigate between at least three different user interface screens, e.g., a hypothetical email reader app might have multiple screens, such as (1) a List view showing all emails, (2) a Detail view showing a single email, (3) a Compose view for creating new emails, and (4) a Settings view for providing information about the user's email account.
* Apps must use at least one advanced capability or API from the following list covered in the MoCCA Specialization: multimedia capture, multimedia playback, touch gestures, sensors, or animation. In addition, experienced students are welcome to use other advanced capabilities not covered in the specialization, such as BlueTooth or Wifi-Direct networking, push notifications, or search, as long as they also use at least one advanced capability or API from the list above. 
* Apps must support at least one operation that is performed off the UI Thread in one or more background Threads or in a Thread pool.  Communication between background Threads and the UI Thread should be handled by at least one of Android concurrency frameworks, such as the HaMeR or AsyncTask framework.

### Functional Requirements

Basic project requirements are common for each project theme and functional requirements define specification for one of those themes. There are Basic Functional Description and App Requirements for Concurrent Daily Selfie:
* A Selfie is an image captured using the Daily Selfie application by the User. The User creates selfies by taking a picture using the phone’s built in camera app, which saves the image to the device at a designated location.
* A Reminder is a unit that holds a time. The User is informed daily to take a Selfie at the time specified in the Reminder.
* The User can view their saved Selfies in an Android ListView. Clicking on a Selfie in the ListView will open a large view in a separate Activity, showing the Selfie in a larger form.
* If the User closes and then reopens the app the user has access to all saved Selfies.
* The User can process Selfies by applying two or more available effects, which can include adding noise, blurring, or adding a charcoal effect to the image. The User selects any number of Selfies to process, selects one or more effects, and then applies the selected effects. 
* Image Processing operations on a Selfie are performed concurrently in a remote web service.  Once processed, the images are returned to the device, where they are saved and displayed in a ListView. 

### Implementation Considerations

There are several questions each student should answer before starting to work on implementation. These considerations are:
* How will Reminders be used to inform the User that it’s time to take a Selfie? For example, will an Android notification be used, so that clicking on the notification will bring the user to the Daily Selfie application?
* How will you store the images on the device? For example, will you use a directory of files, a ContentProvider, etc.?  Likewise, will copies of both the original and filtered images be stored, just the filtered images, etc.?
* What image processing effects/algorithms will you support on the remote web service?  Will you write these in Java or in a native language like C/C++ accessed via the JNI?
* What will the user interface look like for the Daily Selfie app so that it is quick and simple to use? How will there be at least three different user interface screens?
* What, if any, user preferences can the User set? How will the app running on the device, as well as the remote web service, be informed of changes to these user preferences?
* How will the device and the remote web service implement the concurrency requirements, for instance, so that images are processed concurrently (e.g., will you use an AsyncTask on the device and a Java ExecutorService thread pool on the web service)?
* What interface will the remote web service provide to the app running on the device and how will you communicate between the device and the remote web service (e.g., using RetroFit, Android HTTP client, etc.)?
* How will the app use at least one advanced capability or API listed above? For example, will you create an animation to explain how to use the app? How will you allow users to take pictures? Will you use push notifications to prompt Users when to take Selfies?
* Does your app really require two or more fundamental Android components? If so, which ones? For example, this app might benefit from using a ContentProvider or from using a background Service that synchronizes local and remote images to a cloud-based server only when the device is connected to a WiFi network.

### Solution Submission and Grading

To pass the Capstone MOOC, the average of all the Assessor reviews must be >= 70%. Each student will submit a final project deliverable containing the following as a single ZIP file uploaded via the Coursera Peer Assessment mechanism:
* A single PDF design document explaining in detail how they implemented their selected specification. 
* The source code for the server-side must use Java Spring and the source code for the client-side must use Java Android. Since peer reviewers are not required to run the code to grade it, you must provide a PDF with a table that maps the project requirements from the grading rubric to specific classes and/or lines in the source code that the peer reviewer can use to help find where requirements are satisfied without having to hunt through thousands of lines of code and dozens of classes.
* The complete project source code and associated files needed to build the project. Note that all software will ultimately need to be released in open-source form to facilitate peer assessment. Students are responsible for not including proprietary software in their solutions. Students who aren't willing to release their solutions in open-source form can still participate in the Capstone project, but their solutions won't be evaluated and they won't receive credit for passing the Capstone.
* A screencast video showing the working project (submitted separately as a link to the video).

## Project Implementation Description

I decided to modify the name of the application because there are so many uses for the app with such functionality. For example, you can take not selfies but pictures of your kitten and see how he is growing up. Another use is capturing the growth of plants. That is why I think that “Daily Photo” is a better name for my project.
The final projects contains two parts: mobile application and backend server.

### Login

User launches the application and the first screen that is shown is a Login Screen. User cannot access application functionality without entering valid server address, username and password. The server field is present for more convenient and flexible application testing, it should not be present in the release version of application.

If credentials are valid, photo gallery of current user will be opened. Each account has separate gallery. If credentials are invalid, the error will be shown, and user will not proceed to any other screen.

### Photo Gallery

When photo gallery is opened user will see his photos that were previously made. If the photos are not present on the device, they will be downloaded from server.

If user tap on a photo, the larger version will be opened with photo title at the bottom of the screen. User can press Back button to go back to the gallery.
	
There are two buttons on Gallery Screen for taking a photo and for filtering. User can also press back button and return to Login Screen. The gallery will be no longer available until user logins again.

### Taking Photo

To take a photo user needs to press Take Photo button on Gallery Screen. It will open standard camera application. User can go back to gallery without taking a photo. If the photo is taken and accepted, camera application will close and photo will show up in gallery. The photo file is saved on device in Daily Photo directory and also it is uploaded to server.

### Filters

To filter a photo user needs to press Filter Photo button on Gallery Screen. The message that filtering is available will be shown and buttons will change their titles to Cancel and Done. To start filtering user needs to select one or more photos and press Done. After that the checkbox list of filters will appear. User can apply one or more filters to selected photos. Gallery will show updated photos in a moment. Filtering can be applied to the same photos again and again. 

### Notification

Application will send user notification to remind to take a photo. Tapping the notification will launch the application. In release version it should appear one time a day everyday. For testing purposes this time interval was decreased.

## Client Implementation

### Main Activity

Main Activity is represented by MainActivity.java class. It is a core launcher class in the project. It is mentioned in AndroidManifest.xml file.

Main Activity creates and operated all Fragments in application. Its layout contains a container for Fragments. All Fragments of the application are shown in that container. Photo Service Provider is created in this Activity. Fragments communicate with each other and with Photo Service Provider through Main Activity. Notification Service with alarm is launched from this Activity.

### Photo Service Provider

Photo Service Provider is represented by PhotoServiceProvider.java class. Its main assignment is to make called to server using Photo Service API. It stores a photo list of current user that is passed to Gallery Fragment. Photo list is a list of Photo Metadata objects. Photo Service Provider has the next server communication methods:
* *__void login__(String server, String username, String password)* - creates connection to server using Photo Service API and OAuth2.0.
* *__void getPhotoList__()* - executes GetPhotoListTask to get photo list for current user.
* *__void uploadPhoto__(Photo photo)* - executes UploadPhotoTask by given Photo Metadata to load photo to server.
* *__boolean downloadMissingFiles__()* - iterates photo list and checks if each photo from list exists on the phone. If the photo doesn’t exist, it executes DownloadFileTask passing missing photo ID.
* *__void filterPhotos__(ArrayList<Long> photoIds, ArrayList<Integer> filterIds)* - it receives a list of photos to filter and a list of filters. It executes FilterPhotoTask for each photo, passing a list of filters and photo ID. Method executeOnExecutor() was used because regular execute() doesn’t start tasks parallelly, and executeOnExecutor() does.

Photo Service Provider uses AsyncTasks to communicate with server using Photo Service API. AsyncTasks are executed off the main thread, it means that server calls don’t freeze the user interface. AsyncTasks that are used for communication with server are:
* **GetPhotoListTask** - executes photoServiceApi.getPhotoList() that returns a collection of Photo Metadata objects. In onPostExecute() it asks Main Activity to update photos in Gallery Fragment.
* **UploadPhotoTask** - executes photoServiceApi.addPhoto() to add Photo Metadata to server. In onPostExecute() it calls getPhotoList() method to update photo list and starts UploadFileTask to load photo to server.
* **UploadFileTask** - makes photoServiceApi.addPhotoFile() and passes file as TypedFile to save photo on server.
* **DownloadFileTask** - makes photoServiceApi.getPhotoFileById() to download photo from server by photo’s ID. It receives TypedInput from server, converts it to TypedByteArray, created BytesArrayOutputStream from it, ByteArray of which can be written to DataOutputStream. It makes photoServiceApi.getPhotoById() to get Photo Metadata, where local path to file is stored. In onPostExecute() it asks Main Activity to update Gallery Fragment. New file should appear in gallery.
* **FilterPhotoTask** - makes photoServiceApi.filterPhoto() that sends photo id and list of filters to server. It receives filtered photo the same way as DownloadFileTask works. In onPostExecute it asks Main Activity to update Gallery Fragment. New filtered photo will replace the previous one. If the task returns null, it means that more that one request to filter this photo was sent to server. One task will return null when finishes and another one will return photo file with applied filters from both tasks.

### Photo Service API

Photo Service API is represented by PhotoServiceApi.java interface. It uses open-source library Retrofit that turns HTTP API into a Java interface. Annotations on the interface methods and its parameters indicate how a request will be handled. Every method must have an HTTP annotation that provides the request method and relative URL. A request URL can be updated dynamically using replacement blocks and parameters on the method. By default, Retrofit can only deserialize HTTP bodies into OkHttp's ResponseBody type and it can only accept its RequestBody type for @Body. That is why Gson converters was added to support other types.

The Photo Service API is implemented the next way:
```
@GET(“/photo”)
public Collection<Photo> getPhotoList();

@POST("/photo")
public Photo addPhoto(@Body Photo v);

@Multipart
@POST("/photo/upload/{id}")
public Long addPhotoFile(@Part(value = "file") TypedFile file, @Path(value = "id") long id);

@GET("/photo/{id}")
public Photo getPhotoById(@Path("id") long id);

@Streaming
@GET("/photo/{id}/file")
public Response getPhotoFileById(@Path("id") long id);
	
@Streaming
@GET("/photo/filter/{id}/{grayScale}/{sepia}/{invertColors}/{brighten}/{darken}/{blur}/{sharpen}")
public Response filterPhoto(@Path("id") long id, @Path("grayScale") int grayScale, @Path("sepia") int sepia, @Path("invertColors") int invertColors, @Path("brighten") int brighten, @Path("darken") int darken, @Path("blur") int blur, @Path("sharpen") int sharpen);
```
