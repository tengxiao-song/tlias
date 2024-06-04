package com.controller;

import com.aop.Log;
import com.pojo.Dept;
import com.pojo.Result;
import com.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // @Slf4j is a Lombok annotation that generates a logger in the class
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping()
    public Result list() {
        log.info("Query all depts...");
        List<Dept> depts = deptService.list();
        return Result.success(depts);
    }

    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("Delete dept with id: " + id);
        deptService.delete(id);
        return Result.success();
    }

    @Log
    @PostMapping()
    public Result add(@RequestBody Dept dept) {
        log.info("Add dept: " + dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("Query dept with id: " + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    @PutMapping()
    public Result update(@RequestBody Dept dept) {
        log.info("Update dept: " + dept);
        deptService.update(dept);
        return Result.success();
    }
}
