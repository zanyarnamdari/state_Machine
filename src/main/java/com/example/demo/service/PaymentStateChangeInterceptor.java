package com.example.demo.service;


import com.example.demo.model.Payment;
import com.example.demo.model.PaymentEvent;
import com.example.demo.model.PaymentState;
import com.example.demo.repository.PaymentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component

public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentDao paymentRepository;

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message,
                               Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine) {

        Optional.ofNullable(message).flatMap(msg -> Optional.ofNullable((Long) msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L))).ifPresent(paymentId -> {
            Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
            if (optionalPayment.isPresent()){
                Payment payment= optionalPayment.get();
                payment.setState(state.getId());
                paymentRepository.save(payment);
            }
        });
    }

}