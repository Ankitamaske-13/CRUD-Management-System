package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		User user=new User();
		user.setEmail("chhakumaske@gmail.com");
		user.setPassword("1234");
		user.setFirstName("chhakuli");
		user.setLastName("Maske");
		
		User savedUser = repo.save(user);
		
	    User existUser=entityManager.find(User.class, savedUser.getId());
	    assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
	}
	@Test
	public void testFindUserByEmail() {
		String email="chhakumaske@gmail.com";
		User user=repo.findByEmail(email);
		assertThat(user).isNotNull();
	}
}
