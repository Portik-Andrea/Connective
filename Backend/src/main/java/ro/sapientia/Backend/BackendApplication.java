package ro.sapientia.Backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.sapientia.Backend.domains.*;
import ro.sapientia.Backend.repositories.IDepartmentRepository;
import ro.sapientia.Backend.repositories.IGroupRepository;
import ro.sapientia.Backend.repositories.ITaskRepository;
import ro.sapientia.Backend.repositories.IUserRepository;

import java.util.Date;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo_repository1(IDepartmentRepository repository) {
		return (args) -> {
			if(repository.count() == 0){
				repository.save(new Department("Frontend developer"));
				repository.save(new Department("Backend developer"));
				repository.save(new Department("Project manager"));
				repository.save(new Department("DevOps"));
				repository.save(new Department("UX Designer"));
				repository.save(new Department("Business analyst"));
			}

		};
	}

	@Bean
	public CommandLineRunner demo_repository2(IGroupRepository repository) {
		return (args) -> {
			if(repository.count() == 0){
				repository.save(new Group("Applied Linguistics"));
				repository.save(new Group("Applied Social Sciences"));
				repository.save(new Group("Mechanical Engineering"));
				repository.save(new Group("Horticulture"));
				repository.save(new Group("Mathematics-Informatics"));
				repository.save(new Group("Electrical Engineering"));
			}
		};
	}


	/*@Bean
	public CommandLineRunner demo_repository2(IUserRepository repository, IDepartmentRepository departmentRepository) {
		return (args) -> {
			if( repository.count() == 0 ) {
				Optional<Department> department = departmentRepository.findByDepartmentName("Developer");
				// save a few users
				if(department.isPresent()){
					repository.save(new UserEntity("Emese", "Moldovan","moldovan.emese@sonrisa.hu", UserType.MENTOR,department.get(),"password"));
					repository.save(new UserEntity("Sandor", "Ceclan","ceclan.sandor@sonrisa.hu",UserType.MENTEE,department.get(),"password1"));
					repository.save(new UserEntity("Barna", "Petho","peto.barna@sonrisa.hu",UserType.MENTOR,department.get(),"password2"));
					repository.save(new UserEntity("Istvan", "Balint","balint.istvan@sonrisa.hu",UserType.MENTEE,department.get(),"password3"));
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
			Optional<UserEntity> mentee1 = repository.findById(4L);
			Optional<UserEntity> mentor2 = repository.findById(3L);
			if(mentee1.isPresent() && mentor2.isPresent()){
				log.info("Mentee:" + mentee1.get().toString());
				log.info("Mentor:" + mentor2.get().toString());
				mentee1.get().setMentor(mentor2.get());
				repository.save(mentee1.get());
			}
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
	public CommandLineRunner demo_task_repository(ITaskRepository taskRepository, IUserRepository userRepository) {
		return (args) -> {
			if (taskRepository.count() == 0) {
				Optional<UserEntity> mentor = userRepository.findById(1L);
				Optional<UserEntity> user1 = userRepository.findById(2L);
				if(mentor.isPresent() && user1.isPresent()){
					Task task1 = new Task("Set-up company profile","Aaaaa bbbbbb cccc",
							mentor.get(),new Date(123,0,25,19,30),
							Priority.LOW,new Date(1674667800001L),Status.NEW,0);
					task1.setAssignedToUser(user1.get());
					taskRepository.save(task1);
					//userRepository.save(user1.get());

					Task task2 = new Task("Create home page","This is a description",
							mentor.get(),new Date(123,3,29,9,10),
							Priority.MEDIUM,new Date(123,4,20),Status.DONE,100);
					task2.setAssignedToUser(user1.get());
					taskRepository.save(task2);
					userRepository.save(user1.get());
				}
			}
		};
	}*/

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
