package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.InventoryRepository;
import com.example.domains.contracts.services.InventoryService;
import com.example.domains.entities.Inventory;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import lombok.NonNull;

@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	InventoryRepository dao;

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> List<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public List<Inventory> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Inventory> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Inventory> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Inventory> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Inventory add(Inventory item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(dao.existsById(item.getInventoryId()))
			throw new DuplicateKeyException(item.getErrorsMessage());
		
		return dao.save(item);
	}

	@Override
	public Inventory modify(Inventory item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(!dao.existsById(item.getInventoryId()))
			throw new NotFoundException();
		
		return dao.save(item);
	}

	@Override
	public void delete(Inventory item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(item.getInventoryId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

}
