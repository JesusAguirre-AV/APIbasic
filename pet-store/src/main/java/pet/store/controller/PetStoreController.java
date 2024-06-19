package pet.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	PetStoreService petStoreService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData petStoreControl(
			@RequestBody PetStoreData petStoreData) {
		log.info("Creating petStore {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(
			@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating petStore {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee includeEmployee(
			@PathVariable Long petStoreId, 
			@RequestBody PetStoreEmployee petStoreEmployee
			){
		log.info("Including an Employee {}",petStoreEmployee);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer includeCustomer(
			@PathVariable Long petStoreId, 
			@RequestBody PetStoreCustomer petStoreCustomer
			){
		log.info("Including a Customer {}",petStoreCustomer);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@GetMapping
	public List<PetStoreData> listOfPetStores() {
		log.info("Retrieve all petStores.");
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/{petStoreId}")
	public PetStoreData petStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieve petStore by id {}", petStoreId);
		return petStoreService.petStoreById(petStoreId);
	}
	
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId){
		log.info("Delete by Id {}", petStoreId);
		petStoreService.deletePetStoreById(petStoreId);
		Map<String,String> output = new HashMap<String,String>();
		output.put("message", "Deletion successful");
		return output;
	}
}
