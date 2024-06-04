package com.service;

import com.mapper.DeptMapper;
import com.mapper.EmpMapper;
import com.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    // 默认只对RuntimeException进行回滚，可以用rollbackFor指定回滚的异常
    // 如果有多个事务，可以用@Transaction注解指定事务的传播行为。 REQUIRED表示如果有事务就加入，如果异常会一起回滚
    // REQUIRES_NEW会挂起之前的事务，并新建一个独立事务，回滚出现异常的事务
    // 例如：删除部门和员工时，无论成功失败都要记录日志，那么记录日志的method可以用REQUIRES_NEW
    @Transactional(rollbackFor = Exception.class, propagation = REQUIRES_NEW)
    @Override
    public void delete(Integer id) {
        deptMapper.deleteById(id);
        empMapper.deleteByDeptId(id);
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
