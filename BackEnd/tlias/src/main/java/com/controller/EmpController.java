package com.controller;

import com.aop.Log;
import com.pojo.Emp;
import com.pojo.PageBean;
import com.pojo.Result;
import com.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping()
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("Query emps with page: " + page + ", pageSize: " + pageSize + ", name: " + name);
        PageBean pageBean = empService.page(page, pageSize, name, gender, begin, end);
        return Result.success(pageBean);
    }

    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("Delete emps with ids: " + ids);
        empService.delete(ids);
        return Result.success();
    }

    @Log
    @PostMapping()
    public Result save(@RequestBody Emp emp) {
        log.info("Save emp: " + emp);
        empService.save(emp);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("Query emp with id: " + id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    @Log
    @PutMapping()
    public Result update(@RequestBody Emp emp) {
        log.info("Update emp: " + emp);
        empService.update(emp);
        return Result.success();
    }
}
