package com.example.docqa.repository;

import com.example.docqa.entity.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimestampRepository extends JpaRepository<Timestamp, Long> {

    List<Timestamp> findByDocumentId(Long documentId);

    @Query("SELECT t FROM Timestamp t WHERE t.document.id = :documentId AND LOWER(t.text) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Timestamp> searchByKeyword(@Param("documentId") Long documentId, @Param("keyword") String keyword);

}
