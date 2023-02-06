package stb.com.vn.spring.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import stb.com.vn.spring.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Procedure(procedureName = "SP_ADD_NEW_USER")
    long spInsertNewUser(String cifId, String moreInfo);

    @Procedure(procedureName = "SP_GET_USER_BY_ID")
    Optional<User> spGetUserById(long id);

    @Procedure(procedureName = "SP_GET_ALL_USERS")
    List<User> spGetAllUser();
}
