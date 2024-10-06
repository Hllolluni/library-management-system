package com.hllolluni.payment_service.controllers;

import com.hllolluni.payment_service.services.PaymentService;
import com.hllolluni.payment_service.transfers.PaymentResponse;
import com.hllolluni.payment_service.transfers.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    @PreAuthorize("hasAuthority('USER')")
    public PaymentResponse doPayment(HttpServletRequest request, @RequestBody UserInfo user) throws Exception {
        String token = request.getHeader("token");
        return this.paymentService.createPayment(token, user);
    }

    @GetMapping("/checkPayment")
    public Boolean checkPayment(@RequestParam("userId") Long userId, @RequestParam("email") String email) throws Exception {
        return paymentService.checkPayment(userId, email);
    }
}