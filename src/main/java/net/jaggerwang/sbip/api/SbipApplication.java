package net.jaggerwang.sbip.api;

import javax.sql.DataSource;
import com.oembedler.moon.graphql.boot.GraphQLWebsocketAutoConfiguration;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = "net.jaggerwang.sbip",
		exclude = {GraphQLWebsocketAutoConfiguration.class})
@EntityScan("net.jaggerwang.sbip.adapter.repository.jpa.entity")
@EnableJpaRepositories("net.jaggerwang.sbip.adapter.repository.jpa")
@Slf4j
public class SbipApplication implements ApplicationRunner {
	public enum AppType {
		WEB, DB_MIGRATION
	}

	private static AppType appType = AppType.WEB;

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		var appArgs = new DefaultApplicationArguments(args);

		if (appArgs.getOptionValues("app.type") != null
				&& !appArgs.getOptionValues("app.type").isEmpty()) {
			appType = AppType.valueOf(appArgs.getOptionValues("app.type").get(0).toUpperCase());
		}

		new SpringApplicationBuilder(SbipApplication.class)
				.web(appType == AppType.WEB ? WebApplicationType.SERVLET : WebApplicationType.NONE)
				.run(args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (appType == AppType.DB_MIGRATION) {
			migrateDatabase(args);
		}
	}

	private void migrateDatabase(ApplicationArguments args) {
		var flyway = Flyway.configure().dataSource(dataSource).load();
		flyway.migrate();

		log.info("Migrate database finished.");
	}
}
