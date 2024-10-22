package com.example.oracle.dialect;

import org.hibernate.dialect.Oracle12cDialect;

public class OracleDialect extends Oracle12cDialect {

  public OracleDialect() {
    super();
    // TODO: Test This
    // registerColumnType(Types.CLOB, "varchar2(4000)");
  }
}
