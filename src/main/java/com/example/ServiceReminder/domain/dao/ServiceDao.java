package com.example.ServiceReminder.domain.dao;

import com.example.ServiceReminder.domain.dao.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository//DAO(DataAccessObject)クラスに付与するアノテーション
public class ServiceDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate; //JDBC 操作の基本セットを備えたテンプレートクラス

    @Transactional//新規登録メソッド
    public void insert(Service service) {
        final String INSERT = "INSERT INTO SERVICE (service_name,registration_date,period,mail_address,card_brand,card_num,service_id,password,memo) values ("
                + ":serviceName, :registrationDate, :period, :mailAddress, :cardBrand, :cardNum, :serviceId, :password, :memo)";
        jdbcTemplate.update(INSERT, new BeanPropertySqlParameterSource(service));
    }

    //一覧表示メソッド
    public List<Service> view() {
        final String SQL = "SELECT * from service";
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Service.class));
    }

}
