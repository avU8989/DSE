package swa.meet;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import swa.meet.controller.MeetingController;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.Set;

@SpringBootApplication(
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan(
    basePackages = {"swa.meet", "swa.meet.api" , "swa.meet.entities", "swa.meet.controller", "swa.meet.repositories", "swa.meet.services", "org.openapitools.configuration"},
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
public class OpenApiGeneratorApplication {

    private static final Logger logger = LoggerFactory.getLogger(OpenApiGeneratorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OpenApiGeneratorApplication.class, args);
    }

    @Bean(name = "swa.meet.OpenApiGeneratorApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

    @Bean
    public CommandLineRunner commandLineRunner(EntityManager entityManager) {
        return args -> {
            Metamodel metamodel = entityManager.getMetamodel();
            Set<EntityType<?>> entities = metamodel.getEntities();
            for (EntityType<?> entity : entities) {
                logger.info("Entity found: " + entity.getName());
            }
        };
    }

}