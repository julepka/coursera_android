package com.example.dailyphoto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.DataOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;

import com.example.dailyphoto.client.PhotoServiceApi;
import com.example.dailyphoto.filter.ImageFilter;
import com.example.dailyphoto.repository.Photo;
import com.example.dailyphoto.repository.PhotoRepository;
import com.google.common.collect.Lists;

@Controller
@EnableJpaRepositories
public class PhotoServiceController {
	
	@Autowired
    private PhotoRepository photos;
	
	Map<Long, MultipartFile> photoFiles = new HashMap<Long, MultipartFile>();
	Map<Long, ArrayList<Integer>> processingFiles = new HashMap<Long, ArrayList<Integer>>();
	
	ImageFilter imageFilter = new ImageFilter();
	
	@ResponseStatus(value = HttpStatus.OK)
	public class ResultOk extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public class BadRequestException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
	@RequestMapping(value = PhotoServiceApi.PHOTO_SERVICE_PATH, method = RequestMethod.POST)
    public @ResponseBody Photo addPhoto(@RequestBody Photo photo, Principal principal) {
		String owner = principal.getName();
//		ArrayList<Photo> existingPhotos = (ArrayList<Photo>) photos.findByTitle(photo.getTitle());
//		if(existingPhotos != null && existingPhotos.size() > 0) {
//			for(int i = 0; i < existingPhotos.size(); i++) {
//				if((existingPhotos.get(i).getOwner() == owner) && (existingPhotos.get(i).equals(photo))) {
//					photo.setId(existingPhotos.get(i).getId());
//					photos.save(photo);
//					return photo;
//				}
//			}
//			photo.setOwner(owner);
//			photos.save(photo);
//		}
		photo.setOwner(owner);
		Photo newPhoto = photos.save(photo);
		return newPhoto;
	}
	
	@RequestMapping(value = PhotoServiceApi.PHOTO_FILE_UPLOAD_PATH, method = RequestMethod.POST)
	public @ResponseBody Long addPhotoFile(@RequestParam MultipartFile file, @PathVariable(value = "id") long id) {
		photoFiles.put(id, file);
		try {
			new DataOutputStream(new FileOutputStream("" + id)).write(file.getInputStream(), file.getSize());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

//	@RequestMapping(value=PhotoServiceApi.PHOTO_SERVICE_PATH, method=RequestMethod.GET)
//	public @ResponseBody Collection<Photo> getPhotoList(){
//		return Lists.newArrayList(photos.findAll());
//	}
	
	@RequestMapping(value = PhotoServiceApi.PHOTO_SERVICE_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Photo> getPhotoList(Principal principal) {
        return photos.findByOwner(principal.getName());
    }
	
	@RequestMapping(value = PhotoServiceApi.PHOTO_SERVICE_PATH + "/{id}", method = RequestMethod.GET)
	public @ResponseBody Photo getPhotoById(@PathVariable("id") long id) {
		Photo v = photos.findOne(id);
		if (v != null) {
			return photos.findOne(id);	
		} else {
			throw new NotFoundException();
		}
	}
	
	@RequestMapping(value = PhotoServiceApi.PHOTO_SERVICE_PATH + "/{id}/file", method = RequestMethod.GET)
	public @ResponseBody void getPhotoFileById(@PathVariable("id") long id, HttpServletResponse response) {
        InputStream is = null;
        try {
            is = new FileInputStream("" + id);
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@RequestMapping(value = PhotoServiceApi.PHOTO_SERVICE_PATH +
            "/filter/{id}/{grayScale}/{sepia}/{invertColors}/{brighten}/{darken}/{blur}/{sharpen}",
            method = RequestMethod.GET)
    public @ResponseBody void filterPhoto(@PathVariable("id") long id, @PathVariable("grayScale") int grayScale,
                     @PathVariable("sepia") int sepia, @PathVariable("invertColors") int invertColors,
                     @PathVariable("brighten") int brighten, @PathVariable("darken") int darken,
                     @PathVariable("blur") int blur, @PathVariable("sharpen") int sharpen,
                     HttpServletResponse response) {
        System.out.println("Filtering");
        synchronized (processingFiles) {
            if (!processingFiles.containsKey(id)) {
                processingFiles.put(id, new ArrayList<Integer>());
            }
        }
        if (grayScale == 1) processingFiles.get(id).add(0);
        if (sepia == 1) processingFiles.get(id).add(1);
        if (invertColors == 1) processingFiles.get(id).add(2);
        if (brighten == 1) processingFiles.get(id).add(3);
        if (darken == 1) processingFiles.get(id).add(4);
        if (blur == 1) processingFiles.get(id).add(5);
        if (sharpen == 1) processingFiles.get(id).add(6);
        System.out.println("copied filters to array");
        boolean photoFiltered = false;
        synchronized (processingFiles.get(id)) {
            Iterator<Integer> it = processingFiles.get(id).iterator();
            while (!processingFiles.get(id).isEmpty()) {
                Integer filter = it.next();
                switch (filter) {
                    case 0:
                        imageFilter.grayScale("" + id);
                        break;
                    case 1:
                        imageFilter.sepia("" + id);
                        break;
                    case 2:
                        imageFilter.invertColors("" + id);
                        break;
                    case 3:
                        imageFilter.brighten("" + id);
                        break;
                    case 4:
                        imageFilter.darken("" + id);
                        break;
                    case 5:
                        imageFilter.blur("" + id);
                        break;
                    case 6:
                        imageFilter.sharpen("" + id);
                        break;
                }
                System.out.println("filter: " + filter);
                it.remove();
                photoFiltered = true;
            }
            if (photoFiltered) {
                System.out.println("sending file");
                InputStream is = null;
                try {
                    is = new FileInputStream("" + id);
                    org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                    response.flushBuffer();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
}
