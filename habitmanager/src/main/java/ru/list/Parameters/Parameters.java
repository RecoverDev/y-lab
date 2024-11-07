package ru.list.Parameters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameters {

    private DBParameters db;
    private LiquibaseParameters liquibase;

}
