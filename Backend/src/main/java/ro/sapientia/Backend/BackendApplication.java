package ro.sapientia.Backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.sapientia.Backend.domains.Department;
import ro.sapientia.Backend.domains.UserEntity;
import ro.sapientia.Backend.repositories.DepartmentRepository;
import ro.sapientia.Backend.repositories.UserRepository;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo_repository1(DepartmentRepository repository) {
		return (args) -> {
			if(repository.count() == 0){
				repository.save(new Department("HR"));
				repository.save(new Department("Developer"));

			}

		};
	}

	@Bean
	public CommandLineRunner demo_repository2(UserRepository repository, DepartmentRepository departmentRepository) {
		return (args) -> {
			if( repository.count() == 0 ) {
				Optional<Department> department = departmentRepository.findByDepartmentName("Developer");
				// save a few users
				if(department.isPresent()){
					repository.save(new UserEntity("Emese", "Moldovan","moldovan.emese@sonrisa.hu",1,department.get()));
					repository.save(new UserEntity("Sandor", "Ceclan","ceclan.sandor@sonrisa.hu",2,department.get()));
					repository.save(new UserEntity("Barna", "Petho","peto.barna@sonrisa.hu",1,department.get()));
					repository.save(new UserEntity("Istvan", "Balint","balint.istvan@sonrisa.hu",2,department.get()));
				}


			}
//			// fetch all users
//			log.info("Users found with findAll():");
//			log.info("-------------------------------");
//			repository.findAll().forEach( e -> log.info(e.toString()));
//			log.info("Exists user by email: andras.emma@sonrisa.hu");
//			log.info("\tResult: " +
//					repository.existsByEmail("andras.emma@sonrisa.hu"));

			// set mentor
			Optional<UserEntity> mentee = repository.findById(2L);
			Optional<UserEntity> mentor = repository.findById(1L);
			if(mentee.isPresent() && mentor.isPresent()){
				log.info("Mentee:" + mentee.get().toString());
				log.info("Mentor:" + mentor.get().toString());
				mentee.get().setMentor(mentor.get());
				repository.save(mentee.get());
			}
			log.info("**************************************************************************");
			repository.findAll().forEach( e -> log.info(e.toString()));
			log.info("**************************************************************************");
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
