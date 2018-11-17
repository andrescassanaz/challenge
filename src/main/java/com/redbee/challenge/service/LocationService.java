package com.redbee.challenge.service;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.util.RestResponse;

public interface LocationService {
	public Location save(Location location);

	public void delete(Location location);
	
	public Location findById(long id);
	
	public RestResponse addLocation(String locationJson);

	public RestResponse deleteLocation(String locationJson);
	
}
