package org.rtu.heavenhr.recruiting.bff;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecruitingBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitingBffApplication.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.modulesToInstall(new JavaTimeModule());
            jacksonObjectMapperBuilder.featuresToDisable(WRITE_DATES_AS_TIMESTAMPS);
        };
    }

}
