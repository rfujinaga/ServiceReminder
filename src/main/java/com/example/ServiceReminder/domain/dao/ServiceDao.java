package com.example.ServiceReminder.domain.dao;

import com.example.ServiceReminder.domain.dao.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository//DAO(DataAccessObject)クラスに付与するアノテーション
public class ServiceDao {

    @Autowired
        private NamedParameterJdbcTemplate jdbcTemplate; //JDBC 操作の基本セットを備えたテンプレートクラス

    @Transactional//新規登録メソッド
    public void insert(Service service){
        final String INSERT = "INSERT INTO SERVICE (s_name,r_date,t_period,mailaddress,card_bra,card_num,s_id,password,other) values ("
                + ":serviceName, :registrationDate, :period, :mailAddress, :cardBrand, :cardNum, :serviceId, :password, :memo)" ;
        jdbcTemplate.update(INSERT, new BeanPropertySqlParameterSource(service));
    }

    @Transactional//一覧表示メソッド
    public List<Service> view(){
        final String list = "SELECT * from service";
        SqlParameterSource params = new MapSqlParameterSource();//テーブルのparam
        List<Service> services = new ArrayList<>();//テーブルのレコード一覧を格納していくリスト
        for (Map<String,Object> row : jdbcTemplate.queryForList(list,params)) {
            Service service = new Service();
            service.setServiceName((String) row.get("s_name"));
            service.setRegistrationDate((String) row.get("r_date").toString());
            service.setPeriod((String) row.get("t_period").toString());
            service.setMailAddress((String) row.get("mailaddress"));
            service.setCardBrand((String) row.get("card_bra"));
            service.setCardNum((String) row.get("card_num"));
            service.setServiceId((String) row.get("s_id"));
            service.setPassword((String) row.get("password"));
            service.setMemo((String) row.get("other"));
        }
            return services;
    }

}
