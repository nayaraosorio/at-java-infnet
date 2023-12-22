package com.funkostore.spring.datajpa.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funkostore.spring.datajpa.model.FunkoPop;
import com.funkostore.spring.datajpa.repository.FunkoPopRepository;

@RestController
@RequestMapping("/api")
public class FunkoPopController {

    private static final String NEWS_API_KEY = "e01b4e414eea401b900b5948e08c5f09";

	private static final Logger LOGGER = Logger.getLogger(FunkoPopController.class.getName());

	@Autowired
	FunkoPopRepository funkoPopRepository;


	//Lista noticias relacionadas ao Funko usando uma API externa
	@GetMapping("/funkoNews")
		public List<String> getFunkoNews() {
			String keyword = "Funko";
			String apiUrl = "https://newsapi.org/v2/everything?q=" + keyword + "&apiKey=" + NEWS_API_KEY;

			HttpClient httpClient = HttpClient.newHttpClient();
			ArrayList<String> titles = new ArrayList<>();

			try {
				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(apiUrl))
						.build();

				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

				LOGGER.log(Level.INFO, "Status Code: " + response.statusCode());

				String responseBody = response.body();
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode = objectMapper.readTree(responseBody);

				JsonNode articlesNode = jsonNode.path("articles");
				for (JsonNode articleNode : articlesNode) {
					titles.add(articleNode.path("title").asText());
				}

				LOGGER.log(Level.INFO, "News Titles: " + titles);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Error: " + e.getMessage(), e);
			}

			return titles;
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
