package id.user.user_management.repository;

import id.user.user_management.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> , JpaSpecificationExecutor<User> {

    @Query(nativeQuery = true, value = "FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    User findOneByUsername(@Param("username") String username);
}
