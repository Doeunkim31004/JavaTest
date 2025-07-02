package org.zerock.w22.controller;

import lombok.extern.java.Log;
import org.zerock.w22.dto.MemberDTO;
import org.zerock.w22.service.MemberService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
@Log
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        log.info("login get......");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        log.info("login post......");

        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        String auto = req.getParameter("auto");

        boolean rememberMe = auto != null && auto.equals("on");


        try{
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid,mpw);

            if(rememberMe){
                String uuid = UUID.randomUUID().toString();

                MemberService.INSTANCE.updateUuid(uuid,uuid);
                memberDTO.setUuid(uuid);

                Cookie rememberMeCookie = new Cookie("rememberMe",uuid);
                rememberMeCookie.setPath("/");
                rememberMeCookie.setMaxAge(60*60*24*7);

                resp.addCookie(rememberMeCookie);
            }
            HttpSession session = req.getSession();
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/todo/list");

        }catch(Exception e){
            resp.sendRedirect("/login?result=error");
        }
    }
}
