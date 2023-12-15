package com.bezkoder.spring.datajpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.datajpa.model.FunkoPop;
import com.bezkoder.spring.datajpa.repository.FunkoPopRepository;

@RestController
@RequestMapping("/api")
public class FunkoPopController {

	@Autowired
	FunkoPopRepository funkoPopRepository;

	@GetMapping("/hello")
    public String getHello(){
        return "index";
    }    

	@GetMapping("/funkoPops")
	public ResponseEntity<List<FunkoPop>> getAllFunkosPop(@RequestParam(required = false) String title) {
		try {
			List<FunkoPop> funkoPops = new ArrayList<FunkoPop>();

			if (title == null)
				funkoPopRepository.findAll().forEach(funkoPops::add);
			else
				funkoPopRepository.findByTitleContaining(title).forEach(funkoPops::add);

			if (funkoPops.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(funkoPops, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/funkopops/{id}")
	public ResponseEntity<FunkoPop> getFunkoPopById(@PathVariable("id") long id) {
		Optional<FunkoPop> funkoPopData = funkoPopRepository.findById(id);

		if (funkoPopData.isPresent()) {
			return new ResponseEntity<>(funkoPopData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/funkopops")
	public ResponseEntity<FunkoPop> createFunkoPop(@RequestBody FunkoPop funkoPop) {
		try {
			FunkoPop _funkoPop = funkoPopRepository
					.save(new FunkoPop(funkoPop.getTitle(), funkoPop.getDescription(), funkoPop.getPrice(), false));
			return new ResponseEntity<>(_funkoPop, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/funkopops/{id}")
	public ResponseEntity<FunkoPop> updateFunkoPop(@PathVariable("id") long id, @RequestBody FunkoPop funkoPop) {
		Optional<FunkoPop> funkoPopData = funkoPopRepository.findById(id);

		if (funkoPopData.isPresent()) {
			FunkoPop _funkoPop = funkoPopData.get();
			_funkoPop.setTitle(funkoPop.getTitle());
			_funkoPop.setDescription(funkoPop.getDescription());
			_funkoPop.setPrice(funkoPop.getPrice());
			_funkoPop.setAvailable(funkoPop.isAvailable());
			return new ResponseEntity<>(funkoPopRepository.save(_funkoPop), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/funkopops/{id}")
	public ResponseEntity<HttpStatus> deleteFunkoPop(@PathVariable("id") long id) {
		try {
			funkoPopRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/funkopops")
	public ResponseEntity<HttpStatus> deleteAllFunkoPops() {
		try {
			funkoPopRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/funkopops/available")
	public ResponseEntity<List<FunkoPop>> findByAvailable() {
		try {
			List<FunkoPop> funkoPops = funkoPopRepository.findByAvailable(true);

			if (funkoPops.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(funkoPops, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
