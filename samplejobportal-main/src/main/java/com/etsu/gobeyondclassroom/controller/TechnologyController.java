package com.etsu.gobeyondclassroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etsu.gobeyondclassroom.model.Technology;
import com.etsu.gobeyondclassroom.service.TechnologyService;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

	@Autowired
	private TechnologyService technologyService;

	@PostMapping
	public ResponseEntity<Technology> createTechnology(@RequestBody Technology technology) {
		Technology createdTechnology = technologyService.createTechnology(technology);
		return new ResponseEntity<>(createdTechnology, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Technology> getTechnology(@PathVariable Long id) {
		Technology technology = technologyService.getTechnology(id);
		if (technology != null) {
			return new ResponseEntity<>(technology, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Implement other CRUD operations like update and delete

}
