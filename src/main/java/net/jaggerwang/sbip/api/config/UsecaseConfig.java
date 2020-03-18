package net.jaggerwang.sbip.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.jaggerwang.sbip.usecase.UserUsecase;
import net.jaggerwang.sbip.usecase.FileUsecase;
import net.jaggerwang.sbip.usecase.MetricUsecase;
import net.jaggerwang.sbip.usecase.PostUsecase;
import net.jaggerwang.sbip.usecase.StatUsecase;
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
	public UserUsecase userUsecase(UserRepository userRepository, RoleRepository roleRepository,
								   RandomGenerator randomGenerator, PasswordEncoder digestEncoder) {
		return new UserUsecase(userRepository, roleRepository, randomGenerator, digestEncoder);
	}

	@Bean
	public PostUsecase postUsecase(PostRepository postRepository) {
		return new PostUsecase(postRepository);
	}

	@Bean
	public FileUsecase fileUsecase(FileRepository fileRepository, StorageService storageService) {
		return new FileUsecase(fileRepository, storageService);
	}

	@Bean
	public StatUsecase statUsecase(UserStatRepository userStatRepository,
								   PostStatRepository postStatRepository) {
		return new StatUsecase(userStatRepository, postStatRepository);
	}

	@Bean
	public MetricUsecase metricUsecase(MetricRepository metricRepository) {
		return new MetricUsecase(metricRepository);
	}
}
