package com.lagou.dao;

import com.lagou.domain.Test;

import java.util.List;

public interface TestMapper {
    /*
    对test查询

     */
    public List<Test> findAllTest();
}
