package com.redbee.challenge.service;

import com.redbee.challenge.model.Location;

public interface LocationService {
	public Location save(Location location);

	public void delete(Location location);
	
	public Location findById(long id);
}
