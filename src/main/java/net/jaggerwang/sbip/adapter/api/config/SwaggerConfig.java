package net.jaggerwang.sbip.adapter.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Jagger Wang
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        var apiInfo = new ApiInfoBuilder()
                .title("Spring Boot in Practice Api Documentation")
                .description("https://github.com/jaggerwang/spring-boot-in-practice")
                .version("1.0")
                .contact(new Contact("Jagger Wang", "https://blog.jaggerwang.net/", "jaggerwang@gmail.com"))
                .license("MIT")
                .licenseUrl("https://opensource.org/licenses/MIT")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.jaggerwang.sbip.adapter.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);
    }
}
