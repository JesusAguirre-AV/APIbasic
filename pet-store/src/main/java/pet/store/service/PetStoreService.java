package pet.store.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	PetStoreDao petStoreDao;
	@Autowired
	EmployeeDao employeeDao;
	@Autowired
	CustomerDao customerDao;
											//PETSTORE SAVE AND SUPPORT CODE
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAdress(petStoreData.getPetStoreAdress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		petStore.setPetStoreId(petStoreData.getPetStoreId());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore = new PetStore();
		
		if( !Objects.isNull(petStoreId) ) {
			petStore = findPetStoreById(petStoreId);
		}
		return petStore;
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(
				() -> new NoSuchElementException("PetStore with ID="+petStoreId+" was not found."));
	}
											//EMPLOYEE SAVE AND SUPPORT CODE
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId(), petStoreId);
		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		return new PetStoreEmployee(employeeDao.save(employee));
	}
	
	public Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(() ->
				new NoSuchElementException());
		if(petStoreId != employee.getPetStore().getPetStoreId()) {
			throw new IllegalArgumentException();
		}
		return employee;
	}
	
	public Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
		Employee employee = new Employee();
		if(!Objects.isNull(employeeId)) {
			employee = findEmployeeById(petStoreId, employeeId);
		}
		return employee;
	}
	public void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}
											//CUSTOMER SAVE AND SUPPORT CODE
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Customer customer = findOrCreateCustomer(petStoreCustomer.getCustomerId(), petStoreId);
		copyCustomerFields(customer, petStoreCustomer);
		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);
		return new PetStoreCustomer(customerDao.save(customer));
	}
	public Customer findCustomerById(Long petStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId).orElseThrow(() ->
			new NoSuchElementException());
		for(PetStore pS : customer.getPetStores()) {
			if(petStoreId == pS.getPetStoreId()) {
				return customer;
			}
		}
		throw new IllegalArgumentException();
	}
	public Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
		Customer customer = new Customer();
		if(!Objects.isNull(customerId)) {
			customer = findCustomerById(petStoreId, customerId);
		}
		return customer;
	}
	public void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}
										//LISTING ALL PETSTORES
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStoreData> result = new LinkedList<>();
		List<PetStore> petStores = petStoreDao.findAll();
		
		for(PetStore petStore : petStores) {
			PetStoreData psd = new PetStoreData(petStore);
			psd.getCustomers().clear();;
			psd.getEmployees().clear();
			result.add(psd);
		}
		return result;
	}
										//LISTING ONE PETSTORE
	@Transactional(readOnly = true)
	public PetStoreData petStoreById(Long petStoreId) {
		return new PetStoreData(findPetStoreById(petStoreId));
	}
										//DELETING ONE PETSTORE
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
}
