package com.spannerinworks.storycloud.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Story {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;

    @Persistent
    public String title;

    @Persistent
    public String description;

    @Persistent
    public Integer points;

    public Story(String title, String description, String points) {
		this.title = title;
		this.description = description;
		
		if (points != null) {
			this.points = Integer.parseInt(points);
		}
		

	}

}
