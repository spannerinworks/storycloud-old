package com.spannerinworks.storycloud.pages.story;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.tapestry5.annotations.Property;

import com.spannerinworks.storycloud.data.EMF;
import com.spannerinworks.storycloud.entities.Story;

public class CreateStory {

	@Property
	private Story story;
	
	public List<Story> getStories()
    {
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query query = em.createQuery("select from " + Story.class.getName());
			List<Story> res = new ArrayList<Story>();
			for (Object element : query.getResultList()) {
				res.add((Story) element);
			}
			return res;
		} finally {
			em.close();
		}
    }

    Object onSuccess()
    {
    	EntityManager em = EMF.get().createEntityManager();
		try {
			em.persist(story);
		} finally {
			em.close();
		}
        return this;
    }
}
