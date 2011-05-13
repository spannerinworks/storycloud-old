package com.spannerinworks.storycloud.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

import com.spannerinworks.storycloud.data.StoryStatus;

@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonVisual
    public Long id;
    
	@Validate("required,maxLength=140")
	public String title;

	@Validate("required")
	public String description;

	@Validate("min=0")
	public int points;

	@Validate("required")
	public StoryStatus status;
}
