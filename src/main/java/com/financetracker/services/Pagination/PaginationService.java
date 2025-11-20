package com.financetracker.services.Pagination;

import com.financetracker.dto.pagination.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to convert Spring Data Page objects into the custom PaginatedResponse DTO
 */
@Service
@RequiredArgsConstructor
public class PaginationService {

    /**
     * Creates a custom paginated response DTO from a Spring Data Page object.
     * @param pageResult The Page object containing results and pagination metadata.
     * @return The custom PagedResponse DTO.
     */
    public <T> PagedResponse<T> createPaginatedResponse(Page<T> pageResult) {
        List<T> items = pageResult.getContent();
        int page = pageResult.getNumber() + 1; // Spring is 0-indexed, DTO is 1-indexed
        int limit = pageResult.getSize();
        long totalItems = pageResult.getTotalElements();
        int totalPages = pageResult.getTotalPages();

        return new PagedResponse<>(
            items,
            page,
            limit,
            totalItems,
            totalPages
        );
    }
}