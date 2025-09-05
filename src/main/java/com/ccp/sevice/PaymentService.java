package com.ccp.sevice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ccp.entity.Payment;
import com.ccp.model.PaymentRequest;
import com.cpp.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Autowired
    private PaymentRepository paymentRepository;

    public Charge processPayment(PaymentRequest request, String token) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", request.getAmount());
        chargeParams.put("currency", request.getCurreny());
        chargeParams.put("description", request.getDescription());
        chargeParams.put("source", token);

        Charge charge = Charge.create(chargeParams);

        // Save payment details to database
        Payment payment = new Payment();
        payment.setChargeId(charge.getId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurreny());
        payment.setDescription(request.getDescription());
        payment.setStatus(charge.getStatus());
        paymentRepository.save(payment);

        return charge;
    }
}
