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

## Content

* [Project Requirements](#req)
* [Project Implementation Description](#impl)
* [Client Implementation](#client)
* [Server Implementation](#server)
* [Requirements Fulfillment](#req2)

## <a name="req"></a>Project Requirements

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

## <a name="impl"></a>Project Implementation Description

I decided to modify the name of the application because there are so many uses for the app with such functionality. For example, you can take not selfies but pictures of your kitten and see how he is growing up. Another use is capturing the growth of plants. That is why I think that “Daily Photo” is a better name for my project.
The final projects contains two parts: mobile application and backend server.

![Picture](https://github.com/julepka/coursera_android/blob/master/screenshots/andoid.png)

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

## <a name="client"></a>Client Implementation

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

```java
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

### OAuth2.0

OAuth2.0 is represented by EasyHttpClient.java, SecuredRestBuilder.java and SecuredRestException.java classes that are stores in oauth package. EasyHttpClient accepts self signed certificates or invalid server certificates using HTTPS for testing purposes. SecuredRestBuilder is a class that extends RestAdapter.Builder class. It operates user credentials and login process. OAuth2.0 set up was provided by instructors during the course.

### Login Fragment

![Picture](https://raw.githubusercontent.com/julepka/coursera_android/master/screenshots/Screen%20Shot%202016-02-18%20at%203.14.03%20PM.png)

Login Fragment is represented by LoginFragment.java class.

It has three EditTexts for entering server IP address and account credentials: username and password. It also has a Login button that call login() and method which will use Photo Service Provider through Main Activity. After login is complete it requests photo list from Photo Service Provider.

This is the only screen that not authenticated users can see. Not authenticated users should register first (be added to hardcoded list of users) and then they will be able to pass Login Screen. 

### Gallery Fragment

![Picture](https://github.com/julepka/coursera_android/blob/master/screenshots/Screen%20Shot%202016-02-18%20at%203.14.19%20PM.png?raw=true) ![Picture](https://github.com/julepka/coursera_android/blob/master/screenshots/Screen%20Shot%202016-02-18%20at%203.15.50%20PM.png) ![Picture](https://github.com/julepka/coursera_android/blob/master/screenshots/Screen%20Shot%202016-02-18%20at%203.16.09%20PM.png?raw=true) ![Picture](https://github.com/julepka/coursera_android/blob/master/screenshots/Screen%20Shot%202016-02-18%20at%203.16.37%20PM.png?raw=true)

Gallery Fragment is represented by GridFragment.java class. It is a main screen of the application. This Fragment has two modes: regular Gallery mode and Filtering mode for selecting photos to filter. It consists of GridView and two buttons that change depending on the current mode.

If this Fragment is created, it means that client received photo list from server. So, Fragment takes the list and calls downloadMissingPhotos() method of Photo Service provider through Main Activity.

The starting mode of the Gallery Fragment is regular Gallery mode. It has two button: Take Photo and Filter Photo. If user taps one of the photos, it will open Single Photo Fragment.

If user taps Take Photo button, application will create a file for photo and it will send intent to camera application and will wait for result from it. If the result is OK, Photo Service Provider method uploadPhoto() is called. Photo should appear in GridView in a moment. If the result is not OK, file is deleted and nothing is sent to the server.

If user taps Filter Photo button, Fragment’s mode is changed to Filter mode. User will see a toast message that will say to select photos for filtering. Tapping on photo in GridView will select a photo instead of opening Single Photo Fragment. Button titles are changed to Cancel and Done. Pressing Cancel button will deselect all photos and return user to regular Gallery mode. To filter photos user should select one or more photos and press Done button. After that dialog window with checkbox list of filters will appear. User can select one or more filters to be applied to selected photos. After pressing OK button on the dialog Gallery Fragment executes filterPhotos() method of Photo Service Provider. Dialog window disappears and user returns to regular Gallery mode. Application supports the next list of filters:

```
Grayscale (filter id = 0)
Sepia (filter id = 1)
Invert Colors (filter id = 2)
Brighten (filter id = 3)
Darken (filter id = 4)
Blur (filter id = 5)
Sharpen (filter id = 6)
```

### GridView Image Adapter

GridView Image Adapter is represented by GridImageAdapter.java class that extends BaseAdapter of GridView. A specific feature of this Adapter is that it received a list of images but it doesn’t show images in GridView as is. It created a scaled version of each image and shows only scaled images in GridView. It makes loading of GridView Fragment faster and scroll - smoother. 
Single Photo Fragment.

Single Photo Fragment is represented by PhotoFragment.java class. It is very simple class that only has a custom constructor and onCreateView() method. The assignment of this Fragment is to show larger version of selected photo and its title.

### Notification Service

![Picture](https://github.com/julepka/coursera_android/blob/master/screenshots/Screen%20Shot%202016-02-18%20at%203.20.42%20PM.png?raw=true)

Notification Service is represented by NotificationService.java class. Main Activity sets up an AlarmManager to send notification in provided frequency. Notification Service is a sticky IntentService. It means that AlarmManager will send intents to it to show notification periodically. If user taps a notification, application will be launched. 

### Photo Metadata

Photo Metadata is represented by Photo.java class. Objects of this class store data about photos. This class is a POJO. It has the next private properties and setter and getter for each of them:

*  **long id** - unique id that is created on server;

* **String title** - is generated automatically and shows time when photo was created;

* **String url** - local path to the file on the device;

* **String owner** - username of a person who uploaded a photo to server.

### Android Manifest

Android Manifest is represented by AndroidManifest.xml file. Minimum SDK version is set to 16, however it can be easily lowered. Target SDK is set to 21.

Application requires the next permissions:

* Internet - to connect to the server, upload and download files.
* Camera - to take photos.
* Write External Storage - to save and load photos on the device.
* Wake Lock - to send notifications.

Application has only one Activity - Main Activity. Its launch mode is single task and it is cleared on launch. Application has one Service - Notification Service.

## <a name="server"></a>Server Implementation
	
Server is Spring-based and uses Spring boot. API is implemented using Retrofit and it is almost similar to the one that is on the client. The only difference is in some return types. Server has the same Photo POJO as client does, it stores Photos in CrudRepository (repository package). To launch server, Application.java should be run. Major part of server was provided during the course by instructors. The main thing that had to be implemented was a perository and controller.

###Spring Controller

Main operations that server performs are described in PhotoServiceController.java. It is autowired to photos repository. Methods of PhotoServiceController are:

* Photo **addPhoto**(@RequestBody Photo photo, Principal principal) receives Photo Metadata object from client, sets photo owner according to Principal and saves Photo object to repository, where ID is given automatically. It returns Photo object with all fields to the client, so it can save photo’s id.

* Long **addPhotoFile**(@RequestParam MultipartFile file,@PathVariable(value="id")long id) receives file as Multipart object and saves it in server directory with Photo ID as a name of the file.

* Collection<Photo> **getPhotoList**(Principal principal) gets Collection of Photo objects from Photo repository and sends it to the client. It sends only Photos that belong to user who sent a request - Principal.

* Photo **getPhotoById**(@PathVariable("id") long id) finds Photo Metadata object by Photo ID and sends this object back to the client.

* void **getPhotoFileById**(@PathVariable("id") long id, HttpServletResponse response) reads file from server directory to InputStream, copies it to OutputStream of Response and sends Response back to the client.

* void **filterPhoto**(@PathVariable ("id") long id, @PathVariable ("grayScale") int grayScale, @PathVariable ("sepia") int sepia, @PathVariable ("invertColors") int invertColors, @PathVariable ("brighten") int brighten, @PathVariable ("darken") int darken, @PathVariable ("blur") int blur, @PathVariable ("sharpen") int sharpen, HttpServletResponse response) receives Photo ID and filter values: 0 for filter was not applied and 1 for filter was applied. It applies filters concurrently and then saves modified file. After that it places file as InputStream to Response’s OuputStream similar to getPhotoFileById() method and sends it back to the client.

### Server Image Processing Concurrency

Server has HashMap to store which file is currently processing. It has Photo ID as a key and list of filters to apply as value. Any Map that works with concurrency is not suitable to use in current situation because it will prevent server from filtering several photos parallelly at the same time. Synchronized blocks work here perfectly.

The first synchronized block locks HashMap to check if list of filters already exists for current Photo ID. If not, it creates one.

After that it adds filters to the list of corresponding Photo ID key. It is made outside of synchronized blocks.

The second synchronized block locks the filter list of current Photo ID key. A loop is started to apply filters. It reads filter ID from the filter list, applies filter and deletes filter ID from the list. It continues until filter list is empty.

### OAuth2.0

Classes that implement OAuth2.0 user authorisation are places in oauth package. There is a keystore in resource directory, but client ignores certificates for testing purposes.

### Filters

Filters were implemented from scratch in ImageFilter.java class. They are quite simple and don’t work parallelly that makes image processing slower. It was made intentionally to slow down the server to have a chance to test concurrent part of server implementation.

#### Grayscale, Sepia, Invert Colors

The algorithm for these filters contains iterating on every pixel and replacing one with another color depending on what starting color was.

#### Brighten, Darken

These filters are easily implemented using standard Java class RescaleOp. Constructor arguments influence how image will change:

* new RescaleOp(1.2f, 0, null) - for brighten filter
* new RescaleOp(0.5f, 0, null) - for darken filter

#### Blur, Sharpen

The algorithm for these filters is based on convolution matrix (kernel). It means that new color of each pixel depends not only on starting color but also on colors of neighbour pixels.

```
Blur Matrix:		   Sharpen Matrix:
0  0  1  0  0			-1 -1 -1 -1 -1
0  1  1  1  0			-1  2  2  2 -1
1  1  1  1  1			-1  2  8  2 -1
0  1  1  1  0			-1  2  2  2 -1
0  0  1  0  0			-1 -1 -1 -1 -1
```

## <a name="req2"></a>Requirements Fulfillment
### Basic Requirements
#### Multiple Individual User Accounts
Project supports multiple user accounts. Mobile Application has Login Screen to operate user credentials. Server saves owner of each photo it receives to Photo Metadata. When server receives getPhotoList() request, it sends only photos with owner that equals current authenticated user. 

#### One User Facing Operation for Authenticated Users Only
There is only one user facing operation that is available for not authenticated users - Login Screen. All other screens and operations are available only for authenticated users.

#### Two components: Activity, BroadcastReceiver, Service, ContentProvider
Application has Activity - MainActivity.java and Service - NotificationService.java.

#### Interaction with Spring-based Service via HTTP/HTTPS
Mobile application interacts with remote Java Spring-based web service via HTTPS. However it ignores certificates for testing purposes. Retrofit library is used to turn HTTP API into convenient Java interface. Client-Server interraction is described in PhotoServiceAPI.java file.

#### Three User Interface Screens
There are three screens in the application: Login Screen, Gallery Screen, Single Photo Screen. Those screens are Fragments that are placed to MainActivity’s Fragment container.

#### One of: Multimedia Capture or Playback, Gestures, Sensors, Animation
Multimedia capture feature is implemented in Daily Photo. Gallery Fragment sends Intent to camera application to take a photo.

#### One Concurrency Framework: HaMeR, AsyncTask
Several classes extending AsyncTask are implemented in PhotoServiceProvider.java class to communicate with remote web service without blocking the UI thread.

### Functional Requirements
#### Taking and Saving Photo
After passing the Login Screen user can press Take Photo button on Gallery Screen. It will open camera application. If the result in onActivityResult() is OK, it means that photo was taken successfully and it is saved on device. At the same time photo is sent to the server to store it there too.

#### Reminder
It is implemented as AlarmManager and NotificationService combination. It remindes user to take a photo everyday. AlarmManager is set in Main Activity. It send intents to Notification Service to show notification periodically.

#### Viewing Photo List and Photos in Larger Form
Application has Gallery Screen (Gallery Fragment) which basically is a GridView of photos. GridImageAdapter.java describes the way GridView is populated with photos. Tapping on photo in GridView in regular Gallery mode (not in Filtering mode) will open Single Photo Fragment with larger form of the selected photo. Tapping on notification will bring user back to the application.

#### Persisting Photos between Mobile Application Launches
When mobile application closes, photos remain on the device and on the server. When it is opened again, photo list will be received from server and photos will show up. If application is opened from other devices or photos were deleted, they will be downloaded from server.

#### Applying Several Filters to Several Selected Photos
There is a Filter Photo button in Gallery Fragment. User can press it and then select one or more photos and press Done button. After that a list of filters will appear. User can select one or more filters to be applied to selected photos. After pressing OK button, application will create AsyncTask for each selected photo. Each AsyncTask will send server Photo ID and list of filters. When AsyncTask is completed, Gallery is updated and filtered photo will show up.

#### Concurrent Image Processing on Server
Server has filterPhoto() method that can process requests concurrently and can process several photos in parallel way. It has two synchronized blocks that won’t let requests access one resource (photo file) simultaneously. What is more, if server receives two requests to process one photo it will return filtered photo only once with applied filters from both requests.

### Implementation Considerations
How will Reminders be used to inform the User that it’s time to take a Selfie? For example, will an Android notification be used, so that clicking on the notification will bring the user to the Daily Selfie application?
* Yes, Android notifications are used and tapping on the notification will open Daily Photo application.

How will you store the images on the device? For example, will you use a directory of files, a ContentProvider, etc.?  Likewise, will copies of both the original and filtered images be stored, just the filtered images, etc.?
* Directory of files is used to store images. Filtered photo replaces the original one.

What image processing effects/algorithms will you support on the remote web service?  Will you write these in Java or in a native language like C/C++ accessed via the JNI?
* Used effects: grayscale, sepia, invert colors, brighten, darken, blur, sharpen, They are implemented in Java.

What will the user interface look like for the Daily Selfie app so that it is quick and simple to use? How will there be at least three different user interface screens?
* There are just three user interface screens: Login, Gallery and Single Photo Screen. Gallery has two modes: regular and selecting photos for filtering. It shows dialog window to select filters.

What, if any, user preferences can the User set? How will the app running on the device, as well as the remote web service, be informed of changes to these user preferences?
* There are no user preferences.

How will the device and the remote web service implement the concurrency requirements, for instance, so that images are processed concurrently (e.g., will you use an AsyncTask on the device and a Java ExecutorService thread pool on the web service)?
* Mobile application uses AsyncTask for implementing concurrency. Web service uses synchronized blocks.

What interface will the remote web service provide to the app running on the device and how will you communicate between the device and the remote web service (e.g., using RetroFit, Android HTTP client, etc.)?
* Application and web service use Retrofit with Gson. They communicates via HTTP/HTTPS.

How will the app use at least one advanced capability or API listed above? For example, will you create an animation to explain how to use the app? How will you allow Users to take pictures? Will you use push notifications to prompt Users when to take Selfies?
* It allows users to take pictures.

Does your app really require two or more fundamental Android components? If so, which ones? For example, this app might benefit from using a ContentProvider or from using a background Service that synchronizes local and remote images to a cloud-based server only when the device is connected to a WiFi network.
* Application really require Activity component, it can’t exist without it. Remider can be implemented with Service or BroadcastReceiver. Daily Photo uses Service for Reminder.




