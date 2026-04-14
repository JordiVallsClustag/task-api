package me.idrojone.task_api.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import me.idrojone.task_api.domain.model.Task;
import me.idrojone.task_api.domain.repository.TaskRepository;
import me.idrojone.task_api.infrastructure.mapper.TaskDocumentMapper;

@Repository
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskMongoRepository mongoRepository;

    public TaskRepositoryAdapter(TaskMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public List<Task> findAll() {
        return mongoRepository.findAll().stream()
                .map(TaskDocumentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByCategoryId(String categoryId) {
        return mongoRepository.findByCategoryId(categoryId).stream()
                .map(TaskDocumentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(String id) {
        return mongoRepository.findById(id)
                .map(TaskDocumentMapper::toDomain);
    }

    @Override
    public Task save(Task task) {
        var doc = TaskDocumentMapper.toDocument(task);
        var saved = mongoRepository.save(doc);
        return TaskDocumentMapper.toDomain(saved);
    }
}
