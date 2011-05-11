package com.spannerinworks.storycloud.entities;

import org.apache.tapestry5.beaneditor.Validate;

import com.spannerinworks.storycloud.data.StoryStatus;

public class Story {
	@Validate("required,maxLength=140")
	public String title;
	@Validate("required")
	public String description;

	@Validate("min=0")
	public int points;

	@Validate("required")
	public StoryStatus status;
}
