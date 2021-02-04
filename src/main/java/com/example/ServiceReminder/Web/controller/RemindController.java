package com.example.ServiceReminder.Web.controller;

import com.example.ServiceReminder.Web.Form.ServiceForm;
import com.example.ServiceReminder.domain.dao.ServiceDao;
import com.example.ServiceReminder.domain.dao.entity.Service;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.sql.Date;

@Controller
public class RemindController {

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/top")
        public String top(){
                return "top";
    }
    /*
    *TOPページにアクセスした際にDBから現在時刻と契約終了時の差分をリストで保持し、
    * 残り1日or当日のものがあればSlackにてメッセージ送信
    * ｢jdbc.queryForList("SELECT DateDiff(day,CURRENT_DATE(),t_period)FROM service").get(0);｣が
    * エラーの為、コメントアウト
     */

//    public String top(Model model) {
//
//        Map<String, Object> difference = jdbc.queryForList("SELECT DateDiff(day,CURRENT_DATE(),t_period)FROM service").get(0);
//        int dif = Integer.valueOf(difference.toString());
//        List<Integer> datelist = new ArrayList<>();
//
//        datelist.add(dif);
//
//        System.out.println("datelist"+ datelist);
//        if(Arrays.asList(datelist).contains(0)) {
//            try {
//                sendMessage2();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else if(Arrays.asList(datelist).contains(1)) {
//            try {
//                sendMessage1();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else {
//        }
//        return "top";
//    }


    @GetMapping("/new")
    public String registration(Model model) {
        return "new";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("data",serviceDao.view());
        return "list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String id, RedirectAttributes attr) {
        attr.addFlashAttribute("id",jdbc.queryForList("SELECT * FROM service WHERE id = ?",id).get(0).get("id"));
        attr.addFlashAttribute("mailaddress",jdbc.queryForList("SELECT mailaddress FROM service WHERE id = ?",id).get(0).get("mailaddress"));
        attr.addFlashAttribute("card_bra",jdbc.queryForList("SELECT card_bra FROM service WHERE id = ?",id).get(0).get("card_bra"));
        attr.addFlashAttribute("card_num",jdbc.queryForList("SELECT card_num FROM service WHERE id = ?",id).get(0).get("card_num"));
        attr.addFlashAttribute("s_id",jdbc.queryForList("SELECT s_id FROM service WHERE id = ?",id).get(0).get("s_id"));
        attr.addFlashAttribute("password",jdbc.queryForList("SELECT password FROM service WHERE id = ?",id).get(0).get("password"));
        attr.addFlashAttribute("other",jdbc.queryForList("SELECT other FROM service WHERE id = ?",id).get(0).get("other"));
        return "redirect:/detail";
    }

    @GetMapping("/detail")
    public String detail2() {
        return "detail";
    }

    @PostMapping("/delete")
    public String delete(String id, RedirectAttributes attr) {
        attr.addFlashAttribute("id",id);
        jdbc.update("DELETE FROM service WHERE id = ?",id);
        return "redirect:/list";
    }



    /*
    *アプデ済み
    */
    @PostMapping("/post")
    public String Post(ServiceForm form, Model model){
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

    //期間終了日前日メッセージ送信メソッド
//外部ファイルからトークンを読み込む
    public void sendMessage1() throws IOException {
        File file = new File("/Users/rfujinaga/Desktop/slack_tk.txt");
        String token = "";
        if(file.exists()) {

            FileReader filereader = new FileReader(file);

            int data;
            while((data = filereader.read()) != -1) {
                System.out.print((char) data);
                token = token + String.valueOf((char)data);
            }
            filereader.close();
        }
//以降Slackでメッセージを送る
        SlackSession session =
                SlackSessionFactory.createWebSocketSlackSession(token);
        System.out.println(token);
        session.connect();

        SlackChannel channel = session.findChannelByName("fy18-sys-training");
        String message = "明日無料期間が終了するサービスがあります。解約するなら忘れずに！";
        session.sendMessage(channel, message);

        session.disconnect();
    }

    //期間終了日当日メッセージ送信メソッド
    public void sendMessage2() throws IOException {
        File file = new File("/Users/rfujinaga/Desktop/slack_tk.txt");
        String token = "";
        if(file.exists()) {

            FileReader filereader = new FileReader(file);

            int data;
            while((data = filereader.read()) != -1) {
                System.out.print((char) data);
                token = token + String.valueOf((char)data);
            }
            filereader.close();
        }
//以降Slackでメッセージを送る
        SlackSession session =
                SlackSessionFactory.createWebSocketSlackSession(token);
        System.out.println(token);
        session.connect();

        SlackChannel channel = session.findChannelByName("fy18-sys-training");
        String message = "本日が無料期間終了日のサービスがあります。解約するなら忘れずに！";
        session.sendMessage(channel, message);

        session.disconnect();
    }
}