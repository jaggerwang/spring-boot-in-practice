package net.jaggerwang.sbip.api.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import graphql.GraphQL;
import graphql.execution.AsyncExecutionStrategy;
import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import net.jaggerwang.sbip.adapter.graphql.CustomDataFetchingExceptionHandler;
import net.jaggerwang.sbip.adapter.graphql.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Configuration(proxyBeanMethods = false)
public class GraphQLConfig {
    private GraphQL graphQL;

    @Value("classpath:schema.graphqls")
    private Resource schema;

    @Autowired
    QueryType queryType;
    @Autowired
    MutationType mutationType;
    @Autowired
    UserType userType;
    @Autowired
    PostType postType;
    @Autowired
    FileType fileType;
    @Autowired
    UserStatType userStatType;
    @Autowired
    PostStatType postStatType;

    @PostConstruct
    public void init() throws IOException {
        var reader = new InputStreamReader(schema.getInputStream(), StandardCharsets.UTF_8);
        var sdl = FileCopyUtils.copyToString(reader);
        var graphQLSchema = buildSchema(sdl);
        var executionStrategy = new AsyncExecutionStrategy(
                new CustomDataFetchingExceptionHandler());
        this.graphQL = GraphQL.newGraphQL(graphQLSchema)
                .queryExecutionStrategy(executionStrategy)
                .mutationExecutionStrategy(executionStrategy)
                .build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        var typeRegistry = new SchemaParser().parse(sdl);
        var runtimeWiring = buildWiring();
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .scalar(ExtendedScalars.Json)
                .type(newTypeWiring("Query").dataFetchers(queryType.dataFetchers()))
                .type(newTypeWiring("Mutation").dataFetchers(mutationType.dataFetchers()))
                .type(newTypeWiring("User").dataFetchers(userType.dataFetchers()))
                .type(newTypeWiring("Post").dataFetchers(postType.dataFetchers()))
                .type(newTypeWiring("File").dataFetchers(fileType.dataFetchers()))
                .type(newTypeWiring("UserStat").dataFetchers(userStatType.dataFetchers()))
                .type(newTypeWiring("PostStat").dataFetchers(postStatType.dataFetchers()))
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}
