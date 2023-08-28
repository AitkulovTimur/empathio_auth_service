package com.val_pms.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.val_pms.auth.model.Role;
import com.val_pms.auth.model.RoleType;
import com.val_pms.auth.model.User;
import com.val_pms.auth.repository.RoleRepository;
import com.val_pms.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Set;

@SpringBootApplication
@Slf4j
public class ProducerKafkaLearningApplication implements CommandLineRunner {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public static void main(String[] args) {
        SpringApplication.run(ProducerKafkaLearningApplication.class, args);
    }

//    @Bean
//    public ApplicationRunner applicationRunner(final KafkaTemplate<String, String> kafkaTemplate, final KafkaConfigProps kafkaConfigProps) throws JsonProcessingException {
//        final CustomerVisitEvent customerVisitEvent = CustomerVisitEvent.builder()
//                .customerId("Xyi !!!!")
//                .dateTime(LocalDateTime.now())
//                .build();
//
//        final String payload = objectMapper.writeValueAsString(customerVisitEvent);
//
//        return args -> kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
//    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (Boolean.FALSE.equals(userRepository.existsByUsername("bigBoss"))) {
            Role adminTestRole = new Role(RoleType.ROLE_SUPER_ADMIN);

            User userAdmin = User.builder()
                    .email("admin@admin.com")
                    .age(25)
                    .lastName("Admin")
                    .username("bigBoss")
                    .firstName("Admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .roles(Set.of(adminTestRole))
                    .blocked(false)
                    .expirationDateTime(OffsetDateTime.now().plus(Period.ofDays(10)))
                    .build();

            userRepository.save(userAdmin);
        }

        log.info(userRepository.findByUsername("bigBoss").toString());
    }
}
