package com.example.domains.core.contracts.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PagingAndSortingDomainService<E, K> extends DomainService<E, K> {
	List<E> getAll(Sort sort);
	Page<E> getAll(Pageable pageable);
}
