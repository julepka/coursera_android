package com.example.dailyphoto.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for a repository that can store Video
 * objects and allow them to be searched by title.
 * 
 * @author jules
 *
 */
@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long>{

	// Find all videos with a matching title (e.g., Video.name)
	public Collection<Photo> findByOwner(String owner);
	
}
