 package com.slokam.healthcare.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slokam.healthcare.entity.Patient;
import com.slokam.healthcare.pojo.PatientSearchPojo;
import com.slokam.healthcare.repo.Ipatientrepo;
import com.slokam.healthcare.repo.PatientCriteriaRepo;
import com.slokam.healthcare.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

	@Autowired
	private Ipatientrepo patientRepo;

	@Autowired
	private PatientCriteriaRepo patientCriteria;

	@Override
	public void patientRegistration(Patient patient) {
		patient.setRegDate(new Date());
		patientRepo.save(patient);

	}

	@Override
	public List<Patient> criteriaTest(String name, String email) {
		return patientCriteria.patientSearch(name, email);
	}

	@Override
	public List<Patient> patientSearch(PatientSearchPojo searchPojo) {
		return patientCriteria.patientFullSearch(searchPojo);
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepo.findAll();
	}

	@Override
	public Patient getPatientById(Integer id) {
		Optional<Patient> patById =patientRepo.findById(id);
		if (patById.isPresent()) {
			return patById.get();
			
		}
		
		return null;
	}

}
