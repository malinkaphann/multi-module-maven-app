package org.ahlev.database.exception;

public class DuplicateException extends RuntimeException {

    String column;
    String value;

    public DuplicateException(String column, String value) {
        super(String.format("Duplicate entry '%s' for key '%s'", value, column));
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
