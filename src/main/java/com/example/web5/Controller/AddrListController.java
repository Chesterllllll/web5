package com.example.web5.Controller;

import com.example.web5.Entity.AddressList;
import com.example.web5.dao.AddressListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class AddrListController extends HttpServlet {

    public AddressListRepository addressListRepository;

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
    ModelAndView addrList(HttpSession session, Model model) {
        List<AddressList> addressLists = (List<AddressList>) addressListRepository.findAll();
//        model.addAttribute("conList", addressLists);
//        System.out.println(addressLists);

        ModelAndView modelAndView = new ModelAndView("addrList");
        modelAndView.addObject("conList", addressListRepository.findAll());
        return modelAndView;
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


    @ResponseBody
    @GetMapping("/checkphone")
    public String isPhoneExist(@RequestParam(name = "phone") String phone) {
        List<AddressList> addressLists = addressListRepository.findByPhone(phone);
        return addressLists.isEmpty() ? "false" : "true";
    }

    @GetMapping("/delete")
    String delete(@RequestParam Long listId) {
        addressListRepository.deleteById(listId);
        return "redirect:/addrList";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam Long listId, AddressList addressList, Model model) {
        Optional<AddressList> addressListOptional = addressListRepository.findById(listId);
        if (addressListOptional.isPresent()) {
            AddressList addressList1 = addressListOptional.get();
            model.addAttribute("ediCon", addressList1);
            model.addAttribute("conList", addressList1);
            return "editContact";
        } else {
            return "addrList";
        }
    }

    @PostMapping("/edit")
    String edit(AddressList addressList, @RequestParam Long listId) {

        addressList.setId(listId);
        addressListRepository.save(addressList);
        return "redirect:/addrList";
    }

    @RequestMapping("/loginout")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
