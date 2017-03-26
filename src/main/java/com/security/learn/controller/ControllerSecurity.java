package com.security.learn.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by mohammad on 25/3/17.
 */
@Controller
public class ControllerSecurity {


  @GetMapping(value = {"/", "/home"})
  public String homePage(ModelMap model) {
    model.addAttribute("greeting", "Hi, Welcome to mysite");
    return "welcome";
  }

  @GetMapping(value = "/admin")
  public String adminPage(ModelMap model) {
    model.addAttribute("user", getPrincipal());
    return "admin";
  }

  @GetMapping(value = "/db")
  public String dbaPage(ModelMap model) {
    model.addAttribute("user", getPrincipal());
    return "dba";
  }

  @GetMapping(value = "/Access_Denied")
  public String accessDeniedPage(ModelMap model) {
    model.addAttribute("user", getPrincipal());
    return "accessDenied";
  }

  @GetMapping(value = "/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping(value = "/logout")
  public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null){
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login?logout";
  }

  private String getPrincipal(){
    String userName = null;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      userName = ((UserDetails)principal).getUsername();
    } else {
      userName = principal.toString();
    }
    return userName;
  }
}
