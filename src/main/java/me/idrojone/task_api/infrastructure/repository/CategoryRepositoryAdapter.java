package me.idrojone.task_api.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import me.idrojone.task_api.domain.model.Category;
import me.idrojone.task_api.domain.repository.CategoryRepository;
import me.idrojone.task_api.infrastructure.mapper.CategoryDocumentMapper;

@Repository
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryMongoRepository mongoRepository;

    public CategoryRepositoryAdapter(CategoryMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public List<Category> findAll() {
        return mongoRepository.findAll().stream()
                .map(CategoryDocumentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(String id) {
        return mongoRepository.findById(id)
                .map(CategoryDocumentMapper::toDomain);
    }

    @Override
    public Category save(Category category) {
        var doc = CategoryDocumentMapper.toDocument(category);
        var saved = mongoRepository.save(doc);
        return CategoryDocumentMapper.toDomain(saved);
    }
}
