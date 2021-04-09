package com.slokam.healthcare.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.slokam.healthcare.entity.Patient;
import com.slokam.healthcare.pojo.PatientSearchPojo;
import com.slokam.healthcare.util.StringUtils;

@Repository
public class PatientCriteriaRepo {

	@Autowired
	private EntityManager em;

	public List<Patient> patientSearch(String name, String email) {

		// from Patient where name=? and email like ?
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Root<Patient> root = cq.from(Patient.class);

		if (name != null && name.trim().length() > 0) {
			Predicate p = cb.equal(root.get("name"), name);
			predicates.add(p);
		}
		if (email != null && email.trim().length() > 0) {
			Predicate p = cb.like(root.get("email"), "%" + email + "%");
			predicates.add(p);
		}

		cq.where(predicates.toArray(new Predicate[predicates.size()]));

		TypedQuery<Patient> query = em.createQuery(cq);
		return query.getResultList();
	}

	public List<Patient> patientFullSearch(PatientSearchPojo searchPojo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		Root<Patient> root = cq.from(Patient.class);
		List<Predicate> predicateList = new ArrayList<>();
		if (searchPojo != null) {

			if (StringUtils.blankCheck(searchPojo.getName())) {
				predicateList.add(cb.like(root.get("name"), searchPojo.getName() + "%"));
			}

			if (searchPojo.getPhone() != null) {
				predicateList.add(cb.equal(root.get("phone"), searchPojo.getPhone()));
			}

			if (searchPojo.getFromDate() != null) {
				predicateList.add(cb.greaterThanOrEqualTo(root.get("regDate"), searchPojo.getFromDate()));
			}

			if (searchPojo.getToDate() != null) {
				predicateList.add(cb.lessThanOrEqualTo(root.get("regDate"), searchPojo.getToDate()));
			}

			if (searchPojo.getStartingAge() != null) {
				predicateList.add(cb.ge(root.get("age"), searchPojo.getStartingAge()));
			}

			if (searchPojo.getEndingAge() != null) {
				predicateList.add(cb.le(root.get("age"), searchPojo.getEndingAge()));
			}

		}

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Patient> patientQuery = em.createQuery(cq);

		return patientQuery.getResultList();
	}

}
