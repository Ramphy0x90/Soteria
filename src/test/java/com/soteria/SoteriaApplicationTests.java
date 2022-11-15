package com.soteria;

import com.soteria.services.*;
import com.soteria.controllers.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SoteriaApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private UserController userController;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleController roleController;
	@Autowired
	private EntityService entityService;
	@Autowired
	private EntityController entityController;
	@Autowired
	private CredentialService credentialService;
	@Autowired
	private CredentialController credentialController;

	@Test
	void contextLoads() {
		// Test services
		Assertions.assertThat(userService).isNotNull();
		Assertions.assertThat(roleService).isNotNull();
		Assertions.assertThat(entityService).isNotNull();
		Assertions.assertThat(credentialService).isNotNull();
		// Test controllers
		Assertions.assertThat(userController).isNotNull();
		Assertions.assertThat(roleController).isNotNull();
		Assertions.assertThat(entityController).isNotNull();
		Assertions.assertThat(credentialController).isNotNull();
	}

}
