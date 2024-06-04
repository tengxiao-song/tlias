package com.service;

import com.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();
    void delete(Integer id);
    void add(Dept dept);
    Dept getById(Integer id);
    void update(Dept dept);
}
