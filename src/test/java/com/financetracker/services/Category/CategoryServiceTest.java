//package com.financetracker.services.Category;
//
//import com.financetracker.entity.Category;
//import com.financetracker.repository.CategoryRepository;
//import com.financetracker.exception.ResourceNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CategoryServiceTest {
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @InjectMocks
//    private CategoryService categoryService;
//
//    @Test
//    void getAllCategories_ShouldReturnAllCategories() {
//        Category category1 = new Category();
//        category1.setName("Salary");
//        Category category2 = new Category();
//        category2.setName("Rent");
//        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));
//
//        List<Category> categories = categoryService.getAllCategories();
//
//        assertEquals(2, categories.size());
//        verify(categoryRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getCategoryById_ShouldReturnCategory_WhenFound() {
//        Category category = new Category();
//        category.setId(1L);
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//
//        Category foundCategory = categoryService.getCategoryById(1L);
//
//        assertNotNull(foundCategory);
//        assertEquals(1L, foundCategory.getId());
//        verify(categoryRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void getCategoryById_ShouldThrowException_WhenNotFound() {
//        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(99L));
//        verify(categoryRepository, times(1)).findById(99L);
//    }
//}