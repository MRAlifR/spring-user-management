package com.mralifr.arisan;

import com.mralifr.arisan.domain.models.Role;
import com.mralifr.arisan.domain.models.User;
import com.mralifr.arisan.domain.ports.inputs.RoleInputPort;
import com.mralifr.arisan.domain.ports.inputs.UserInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ArisanApplication implements ApplicationRunner {

	private final RoleInputPort roleInputPort;
	private final UserInputPort userInputPort;

	@Autowired
	public ArisanApplication(RoleInputPort roleInputPort, UserInputPort userInputPort) {
		this.roleInputPort = roleInputPort;
		this.userInputPort = userInputPort;
	}


	public static void main(String[] args) {
		SpringApplication.run(ArisanApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (userInputPort.count() == 0 && roleInputPort.count() == 0) {
			Role role = new Role();
			role.setName("ROLE_ADMIN");
			Role savedRole = roleInputPort.createRole(role);

			User user = new User();
			user.setUsername("admin");
			user.setPassword("admin");
			user.setEmail("admin@gmail.com");
			user.setRoles(Collections.singleton(savedRole));
			User savedUser = userInputPort.registerUser(user);
		}

	}
}
