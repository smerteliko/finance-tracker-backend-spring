package com.financetracker.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Paginated response wrapper")
public class PagedResponse<T> {

    @Schema(description = "List of items for the current page")
    private List<T> content;

    @Schema(description = "Current page number")
    private int currentPage;

    @Schema(description = "Page size")
    private int pageSize;

    @Schema(description = "Total number of elements")
    private long totalElements;

    @Schema(description = "Total number of pages")
    private int totalPages;

    @Schema(description = "Is this the first page?")
    private boolean first;

    @Schema(description = "Is this the last page?")
    private boolean last;

    public PagedResponse(List<T> content, int currentPage, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = currentPage == 0;
        this.last = currentPage == totalPages - 1;
    }
}
