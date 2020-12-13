package com.example.web5.Controller;

import com.example.web5.Entity.AddressList;
import com.example.web5.dao.AddressListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AddrListController {

    private AddressListRepository addressListRepository;

    @Autowired
    public void setCustomerRepository(AddressListRepository addressListRepository) {
        this.addressListRepository = addressListRepository;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (null == username || null == password) {
            return "redirect:/";
        }
        //不正确的用户名密码
        if (!username.equals("admin") || !password.equals("123456")) {
            //登录失败设置标志位null 因为没有登出
            request.getSession().setAttribute("loginName", null);
            return "redirect:/";
        }
        //登陆成功 登录用户名为admin
        request.getSession().setAttribute("loginName", "admin");
        return "redirect:/addrList";//成功转到联系人列表页面
    }

    @RequestMapping("/addrList")
//联系人列表页面
    String addrList(HttpSession session, Model model) {
        List< AddressList> addressLists = (List<AddressList>) addressListRepository.findAll();
//        model.addAttribute("conList", addressLists);
        System.out.println(addressLists);
        model.addAttribute("conList", addressLists);
        return "addrList";
    }

    //添加联系人页面
    @GetMapping("/add")
    String addContact() {
        return "addContact";
    }

    @PostMapping("/add")
    String add(AddressList addressList) {
        addressListRepository.save(addressList);
        return "redirect:/addrList";
    }
//
//    @GetMapping("/delete")
//    String delete(@RequestParam int listId, Model model) {
//        mapList.remove(listId);
//        model.addAttribute("conList", mapList);
//        return "redirect:/addrList";
//    }
//
//    @RequestMapping("/edit")
//    String edit(@RequestParam int listId, Model model, HttpServletRequest request) {
//        String name = request.getParameter("name");
//        String phone = request.getParameter("phone");
//        String email = request.getParameter("email");
//        String address = request.getParameter("address");
//        String qq = request.getParameter("qq");
//        if (name != null) {
//            Map<String, Object> temp = new HashMap<String, Object>() {
//                {
//                    put("name", name);
//                    put("phone", phone);
//                    put("email", email);
//                    put("address", address);
//                    put("qq", qq);
//                }
//            };
//            mapList.set(listId, temp);
//            model.addAttribute("conList", mapList);
//            return "redirect:/addrList";
//        }
//        model.addAttribute("listId", listId);
//        model.addAttribute("ediCon", mapList.get(listId));
//        return "editContact";
//    }

    @RequestMapping("/loginout")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
