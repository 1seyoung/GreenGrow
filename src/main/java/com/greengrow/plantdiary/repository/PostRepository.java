package com.greengrow.plantdiary.repository;

import com.greengrow.plantdiary.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본 CRUD 메소드들은 JpaRepository에 의해 제공됩니다.
    // 여기에 필요한 추가적인 쿼리 메소드를 정의할 수 있습니다.
}
