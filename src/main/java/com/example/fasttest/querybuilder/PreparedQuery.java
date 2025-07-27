package com.example.fasttest.querybuilder;

public record PreparedQuery(String sql,
                            Object[] params) {

}
