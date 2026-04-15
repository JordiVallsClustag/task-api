package me.idrojone.task_api.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import me.idrojone.task_api.domain.model.Task;
import me.idrojone.task_api.domain.repository.TaskRepository;
import me.idrojone.task_api.infrastructure.mapper.TaskDocumentMapper;
import me.idrojone.task_api.infrastructure.model.TaskDocument;

@Repository
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskMongoRepository mongoRepository;
    private final MongoTemplate mongoTemplate;

    public TaskRepositoryAdapter(TaskMongoRepository mongoRepository, MongoTemplate mongoTemplate) {
        this.mongoRepository = mongoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Task> findAll() {
        return mongoRepository.findAll().stream()
                .map(TaskDocumentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll(int offset, int limit) {
        Query query = new Query().skip(offset).limit(limit);
        List<TaskDocument> docs = mongoTemplate.find(query, TaskDocument.class);
        return docs.stream().map(TaskDocumentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll(int offset, int limit, Boolean deleted) {
        Query query = new Query();
        if (deleted != null) {
            query.addCriteria(Criteria.where("deleted").is(deleted));
        }
        query.skip(offset).limit(limit);
        List<TaskDocument> docs = mongoTemplate.find(query, TaskDocument.class);
        return docs.stream().map(TaskDocumentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Task> findByCategoryId(String categoryId) {
        return mongoRepository.findByCategoryId(categoryId).stream()
                .map(TaskDocumentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByCategoryId(String categoryId, int offset, int limit) {
        Query query = new Query(Criteria.where("categoryId").is(categoryId)).skip(offset).limit(limit);
        List<TaskDocument> docs = mongoTemplate.find(query, TaskDocument.class);
        return docs.stream().map(TaskDocumentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Task> findByCategoryId(String categoryId, int offset, int limit, Boolean deleted) {
        Query query = new Query(Criteria.where("categoryId").is(categoryId));
        if (deleted != null) {
            query.addCriteria(Criteria.where("deleted").is(deleted));
        }
        query.skip(offset).limit(limit);
        List<TaskDocument> docs = mongoTemplate.find(query, TaskDocument.class);
        return docs.stream().map(TaskDocumentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(String id) {
        return mongoRepository.findById(id)
                .map(TaskDocumentMapper::toDomain);
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), TaskDocument.class);
    }

    @Override
    public long count(Boolean deleted) {
        Query query = new Query();
        if (deleted != null) {
            query.addCriteria(Criteria.where("deleted").is(deleted));
        }
        return mongoTemplate.count(query, TaskDocument.class);
    }

    @Override
    public long countByCategoryId(String categoryId) {
        Query query = new Query(Criteria.where("categoryId").is(categoryId));
        return mongoTemplate.count(query, TaskDocument.class);
    }

    @Override
    public long countByCategoryId(String categoryId, Boolean deleted) {
        Query query = new Query(Criteria.where("categoryId").is(categoryId));
        if (deleted != null) {
            query.addCriteria(Criteria.where("deleted").is(deleted));
        }
        return mongoTemplate.count(query, TaskDocument.class);
    }

    @Override
    public Task save(Task task) {
        var doc = TaskDocumentMapper.toDocument(task);
        var saved = mongoRepository.save(doc);
        return TaskDocumentMapper.toDomain(saved);
    }

    @Override
    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }
}
