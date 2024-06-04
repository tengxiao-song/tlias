package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// for combine rows and number of rows (from query all emps)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private Long total;
    private List rows;
}
