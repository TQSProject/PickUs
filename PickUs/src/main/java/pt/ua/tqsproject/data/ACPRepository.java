package pt.ua.tqsproject.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ACPRepository extends JpaRepository<ACP, Long> {
}
