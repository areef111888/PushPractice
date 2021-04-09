package com.slokam.healthcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slokam.healthcare.repo.Ivisitingrepo;
import com.slokam.healthcare.service.IvisitingService;

@Service
public class VisitingServiceImpl implements IvisitingService {
	
	@Autowired
	private Ivisitingrepo visitingrepo;

	@Override
	public List<Object[]> getVisitingDetailsById(Integer id) {
		List<Object[]> l =visitingrepo.getVisitingDetailsById(id);
		return l;
	}

}
