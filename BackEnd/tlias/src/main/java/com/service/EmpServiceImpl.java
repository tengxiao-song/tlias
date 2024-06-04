package com.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.EmpMapper;
import com.pojo.Emp;
import com.pojo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService{
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
//        分页查询
//        method1: 分两步查询， 先查询总数，再计算并查询分页数据
//        Long total = empMapper.count();
//        List<Emp> emps = empMapper.page((page - 1) * pageSize, pageSize);

//        method2: 使用pagehelper插件
        PageHelper.startPage(page, pageSize); // 设置分页参数
        List<Emp> emps = empMapper.list(name, gender, begin, end); // 自动添加limit start, pageSize进行分页查询，并自动查询总数
        Page<Emp> p = (Page<Emp>) emps; // 向下转型使用其方法
        return new PageBean(p.getTotal(), p.getResult()); // getTotal()获取总数，getResult()获取分页数据
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Override
    public Emp login(Emp emp) {
        return empMapper.getByUsernameAndPassword(emp);
    }
}
