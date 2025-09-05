package com.ccp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccp.entity.Payment;
import com.ccp.model.PaymentRequest;
import com.ccp.service.PaymentService;
import com.cpp.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
@RequestMapping("/api")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	@Autowired
    private PaymentRepository paymentRepository;

	
	@PostMapping("/pay")
	public ResponseEntity<String> makePayment(@RequestBody PaymentRequest paymentRequest,
			@RequestParam("token")String token){
		try {
			Charge charge=paymentService.processPayment(paymentRequest, token);
			return ResponseEntity.ok("Payment SuccessFull! chargeId:"+charge.getId());
			
		}
		catch(StripeException e) {
			return ResponseEntity.badRequest().body("PaymentFailed!:"+e.getMessage());
		}
		
	}
	@GetMapping("/payments")
    public List<Payment> getPaymentHistory() {
        return paymentRepository.findAll();
    }
}
