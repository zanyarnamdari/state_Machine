package com.example.demo.service;

import com.example.demo.model.Payment;
import com.example.demo.model.PaymentEvent;
import com.example.demo.model.PaymentState;
import com.example.demo.repository.PaymentDao;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentDao paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("20.55")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);
        StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());
        Optional<Payment> preAuthPaymentOptional = paymentRepository.findById(savedPayment.getId());
        if (preAuthPaymentOptional.isPresent()){
            Payment preAuthPayment= preAuthPaymentOptional.get();
            System.out.println(sm.getState().getId());
            System.out.println(preAuthPayment);
        }

    }
}