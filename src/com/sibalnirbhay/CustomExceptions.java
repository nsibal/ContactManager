package com.sibalnirbhay;

/**************************************************************************
 * Custom Exceptions
 **************************************************************************/

/**************************************************************************
 * NullFieldException is invoked if the user hasn't entered any value for a
 * field which has NOT NULL Constraint on it.
 **************************************************************************/

class NullFieldException extends Exception {
    NullFieldException(String field) {
        super(field);
    }
}

/**************************************************************************
 * DataTooLongException is invoked if the size of user's input for a field
 * is greater than the maximum size allowed in the database.
 **************************************************************************/

class DataTooLongException extends Exception {
    DataTooLongException(String field) {
        super(field);
    }
}

/**************************************************************************
 * InvalidDateException is invoked if the user enters a date whose format
 * is valid, but the date is either prior to January 01, 1850 or later than
 * current system date.
 **************************************************************************/

class InvalidDateException extends Exception {
    InvalidDateException(String field) {
        super(field);
    }
}

/**************************************************************************
 * DuplicateDataException is invoked for fields with UNIQUE constraint on
 * them.
 **************************************************************************/

class DuplicateDataException extends Exception {
    DuplicateDataException(String field) {
        super(field);
    }
}
