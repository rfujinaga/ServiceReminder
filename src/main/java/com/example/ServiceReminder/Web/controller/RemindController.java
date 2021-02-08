package com.example.ServiceReminder.Web.controller;

import com.example.ServiceReminder.Web.Form.ServiceForm;
import com.example.ServiceReminder.domain.dao.ServiceDao;
import com.example.ServiceReminder.domain.dao.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;


@Controller
public class RemindController {

    @Autowired
    private ServiceDao serviceDao;

    //TOP画面
    @GetMapping("/top")
    public String top() {
        return "top";
    }

    //新規登録画面
    @GetMapping("/new")
    public String registration() {
        return "new";
    }

    //新規登録画面の登録ボタン押下で以下処理
    //insert後は一覧表示画面へ
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

    //一覧表示画面、viewAllメソッドでDB内全件表示
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("data", serviceDao.viewAll());
        return "list";
    }

    //特定レコードの詳細情報表示画面
    //IDをキーとして、list.htmlでは表示されてない詳細情報を表示
    //RedirectAttributesを使用することでURLにパラメータを表示しない
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, RedirectAttributes attr) {
        Map<String,Object> serviceMap = serviceDao.viewDetail(id);
        Service service = new Service();
        service.setMailAddress((String) serviceMap.get("mail_address"));
        service.setCardBrand((String) serviceMap.get("card_brand"));
        service.setCardNum((String) serviceMap.get("card_num"));
        service.setServiceId((String) serviceMap.get("service_id"));
        service.setPassword((String) serviceMap.get("password"));
        service.setMemo((String) serviceMap.get("memo"));
        attr.addFlashAttribute("service",service);
        return "redirect:/detail";
    }

    //これいる？
    @GetMapping("/detail")
    public String detail2() {
        return "detail";
    }

    //削除機能
    //詳細表示と同じくIDをキーとして削除実行
    //削除後はlist画面へリダイレクト
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        serviceDao.delete(id);
        return "redirect:/list";
    }

}
