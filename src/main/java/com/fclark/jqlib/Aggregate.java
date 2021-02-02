package com.fclark.jqlib;

import com.fclark.jqlib.column.Column;


public interface Aggregate {
    Column sum();
    Column count();
    Column avg();
    Column min();
    Column max();
}
