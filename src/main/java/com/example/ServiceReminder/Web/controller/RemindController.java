package com.example.ServiceReminder.Web.controller;

import com.example.ServiceReminder.Web.Form.ServiceForm;
import com.example.ServiceReminder.domain.dao.ServiceDao;
import com.example.ServiceReminder.domain.dao.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;


@Controller
public class RemindController {

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private JdbcTemplate jdbc;

    /*
     *アプデ済
     */
    @GetMapping("/top")
    public String top() {
        return "top";
    }

    /*
     *アプデ済
     */
    @GetMapping("/new")
    public String registration() {
        return "new";
    }

    /*
    *アプデ済
     */
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("data", serviceDao.viewAll());
        return "list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,Model model) {
        Map<String,Object> serviceMap = serviceDao.viewDetail(id);
        Service service = new Service();
        service.setMailAddress((String) serviceMap.get("mail_address"));
        service.setCardBrand((String) serviceMap.get("card_brand"));
        service.setCardNum((String) serviceMap.get("card_num"));
        service.setServiceId((String) serviceMap.get("service_id"));
        service.setPassword((String) serviceMap.get("password"));
        service.setMemo((String) serviceMap.get("memo"));
        model.addAttribute("service",service);
        return "detail";
    }

    //これいる？
    @GetMapping("/detail")
    public String detail2() {
        return "detail";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        serviceDao.delete(id);
        return "redirect:/list";
    }


    /*
     *アプデ済み
     */
    @PostMapping("/post")
    public String Post(ServiceForm form, Model model) {
        Service service = new Service();
        service.setServiceName(form.getServiceName());
        service.setRegistrationDate(form.getRegistrationDate());
        service.setPeriod(form.getPeriod());
        service.setMailAddress(form.getMailAddress());
        service.setCardBrand(form.getCardBrand());
        service.setCardNum(form.getCardNum());
        service.setPassword(form.getPassword());
        service.setMemo(form.getMemo());
        serviceDao.insert(service);
        return "redirect:/list";
    }
}
