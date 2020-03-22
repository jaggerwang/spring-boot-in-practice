package net.jaggerwang.sbip.adapter.graphql.type;

import graphql.schema.DataFetcher;

import java.util.Map;

public interface Type {
    Map<String, DataFetcher> dataFetchers();
}
