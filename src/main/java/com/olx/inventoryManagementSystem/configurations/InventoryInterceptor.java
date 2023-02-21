package com.olx.inventoryManagementSystem.configurations;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//public class InventoryInterceptor extends HandlerInterceptorAdapter {
//
////    @Override
////    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
////            throws Exception {
////        // TODO Auto-generated method stub
////
////    }
//
////    @Override
////    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
////            throws Exception {
////        // TODO Auto-generated method stub
////
////    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//
//        String emailAddress = request.getParameter("emailaddress");
//        String password = request.getParameter("password");
//
//        if(StringUtils.isEmpty(emailAddress) || StringUtils.containsWhitespace(emailAddress) ||
//                StringUtils.isEmpty(password) || StringUtils.containsWhitespace(password)) {
//            throw new Exception("Invalid User Id or Password. Please try again.");
//        }
//
//        return true;
//    }
//
//
//}
