package net.jaggerwang.sbip.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.jaggerwang.sbip.usecase.UserUsecases;
import net.jaggerwang.sbip.usecase.AuthorityUsecases;
import net.jaggerwang.sbip.usecase.FileUsecases;
import net.jaggerwang.sbip.usecase.MetricUsecases;
import net.jaggerwang.sbip.usecase.PostUsecases;
import net.jaggerwang.sbip.usecase.StatUsecases;
import net.jaggerwang.sbip.usecase.port.encoder.PasswordEncoder;
import net.jaggerwang.sbip.usecase.port.generator.RandomGenerator;
import net.jaggerwang.sbip.usecase.port.repository.RoleRepository;
import net.jaggerwang.sbip.usecase.port.repository.FileRepository;
import net.jaggerwang.sbip.usecase.port.repository.MetricRepository;
import net.jaggerwang.sbip.usecase.port.repository.PostRepository;
import net.jaggerwang.sbip.usecase.port.repository.PostStatRepository;
import net.jaggerwang.sbip.usecase.port.repository.UserRepository;
import net.jaggerwang.sbip.usecase.port.repository.UserStatRepository;
import net.jaggerwang.sbip.usecase.port.service.StorageService;

@Configuration
public class UsecaseConfig {
	@Bean
	public UserUsecases userUsecases(UserRepository userRepository, RandomGenerator randomGenerator,
			PasswordEncoder digestEncoder) {
		return new UserUsecases(userRepository, randomGenerator, digestEncoder);
	}

	@Bean
	public PostUsecases postUsecases(PostRepository postRepository) {
		return new PostUsecases(postRepository);
	}

	@Bean
	public FileUsecases fileUsecases(FileRepository fileRepository, StorageService storageService) {
		return new FileUsecases(fileRepository, storageService);
	}

	@Bean
	public StatUsecases statUsecases(UserStatRepository userStatRepository,
			PostStatRepository postStatRepository) {
		return new StatUsecases(userStatRepository, postStatRepository);
	}

	@Bean
	public AuthorityUsecases authorityUsecases(RoleRepository roleRepository) {
		return new AuthorityUsecases(roleRepository);
	}

	@Bean
	public MetricUsecases metricUsecases(MetricRepository metricRepository) {
		return new MetricUsecases(metricRepository);
	}
}
