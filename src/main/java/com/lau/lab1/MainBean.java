package com.lau.lab1;

import com.lau.lab1.DatabaseConnection.DataSource;
import com.lau.lab1.Service.Model;
import com.lau.lab1.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainBean {
    @Bean
    public DataSource dataSource() {
        return new DataSource();
    }
    @Bean
    public BaseProbaRepository probaRepository() {
        return new ProbaRepository(dataSource());
    }
    @Bean
    public BaseUserRepository userRepository() {
        return new UserRepository(dataSource());
    }
    @Bean
    public BaseParticipantRepository participantRepository() {
        return new ParticipantRepository(dataSource());
    }
    @Bean
    public IBaseParticipantProbaRepositoryFactory factory() {
        return new ParticipantProbaRepositoryFactory(dataSource());
    }
    @Bean
    public Model model() {
        return new Model(userRepository(), participantRepository(), factory(), probaRepository());
    }
}
