
package zup.repository;

import org.springframework.data.repository.CrudRepository;
import zup.common.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByLogin(String login);
}
