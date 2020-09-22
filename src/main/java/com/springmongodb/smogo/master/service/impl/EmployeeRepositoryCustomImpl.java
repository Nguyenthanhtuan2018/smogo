package com.springmongodb.smogo.master.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.springmongodb.smogo.master.entity.Employee;
import com.springmongodb.smogo.master.service.EmployeeRepositoryCustom;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long getMaxEmpId() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.limit(1);
        Employee maxObject = mongoTemplate.findOne(query, Employee.class);
        if (maxObject == null) {
            return 0L;
        }
        return maxObject.getId();
    }

    @Override
    public long updateEmployee(String employeeNo, String name, Date hireDate) {
        Query query = new Query(Criteria.where("employeeNo").is(employeeNo));
        Update update = new Update();
        update.set("name", name);
        update.set("hireDate", hireDate);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Employee.class);

        if (result != null) {
            return result.getModifiedCount();
        }

        return 0;
    }

}
