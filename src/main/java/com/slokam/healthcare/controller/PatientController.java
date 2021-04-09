package com.slokam.healthcare.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slokam.healthcare.entity.Patient;
import com.slokam.healthcare.pojo.PatientSearchPojo;
import com.slokam.healthcare.service.IPatientService;
@RestController
@RequestMapping("patient")
public class PatientController {
	@Autowired
	private IPatientService patientService;
	
	@PostMapping("/patientRegistration")
	public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
		patientService.patientRegistration(patient);
		return new ResponseEntity<Patient>(patient,HttpStatus.CREATED);

	}
	
	@GetMapping("/criteriaTest/{name}/{email}")
	public ResponseEntity<List<Patient>> criteriaTest(@PathVariable String name,@PathVariable String email) {
		List<Patient> list = patientService.criteriaTest(name, email);
		return new ResponseEntity<List<Patient>>(list,HttpStatus.OK);
	}
	@PostMapping("/search")
	public ResponseEntity<List<Patient>> patientSearch(@RequestBody PatientSearchPojo searchPojo) {
		List<Patient> list=  patientService.patientSearch(searchPojo);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/getAllPatients")
	public ResponseEntity<List<Patient>> getAll() {
		List<Patient> list=  patientService.getAllPatients();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/getPatientById/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Integer id){
		Patient patient = patientService.getPatientById(id);
		return ResponseEntity.ok(patient);
	}
	
	@GetMapping("/getAllEvenPatients")
	public ResponseEntity<List<Patient>> getAllEvenPatients() {
		/*List<Patient> list=  patientService.getAllPatients();
		List<Patient> evenlist =list.stream().filter(patient->patient.getId()%2==0).collect(Collectors.toList());*/
		
		return ResponseEntity.ok(patientService.getAllPatients().stream(
				).filter(patient->patient.getId()%2==0).collect(Collectors.toList()));
	}

}
