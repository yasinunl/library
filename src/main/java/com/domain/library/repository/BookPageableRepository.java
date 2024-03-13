package com.domain.library.repository;

import com.domain.library.dto.BooksDTO;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookPageableRepository extends PagingAndSortingRepository<BooksDTO, Integer> {
}
