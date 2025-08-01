package com.mgt.controller;

import org.springframework.web.bind.annotation.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mgt.model.Transaction;
import com.mgt.model.User;
import com.mgt.repository.TransactionRepository;
import com.mgt.repository.UserRepo;
import com.mgt.serviceimpl.AuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

     @Autowired
    private TransactionRepository transactionRepo;

     @Autowired
    private AuthUserService authenticatedUserService;

    @Autowired
    private UserRepo userRepo;

    // Create new payment transaction
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPayment(@RequestParam Double amount, 
                                           @RequestParam String transactionId,
                                            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
                                            ) {
   
        try {
            User user = authenticatedUserService.getAuthenticatedUser(authorizationHeader);
             String upiId = "8446898397@ybl"; // replace with your UPI ID
        String name = "Jaykumar";
        String upiLink = "upi://pay?pa=" + upiId + "&pn=" + name + "&am=" + amount + "&cu=INR";

        Transaction txn = new Transaction();
        txn.setTransactionId(transactionId);
        txn.setUpiLink(upiLink);
        txn.setAmount(amount);
        txn.setStatus("PENDING");
        txn.setUser(user);
        transactionRepo.save(txn);

       user.setPaymentStatus(true);

       userRepo.save(user);     

        return ResponseEntity.ok(Map.of("message","Transiction Saved"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while creating product: " + e.getMessage());
            
        }
       
    }

    @GetMapping(value = "/generateQR", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] generateQR(@RequestParam String amount) throws Exception {
        String upiId = "8446898397@ybl"; // replace with your UPI ID
        String name = "Jaykumar";
        String upiLink = "upi://pay?pa=" + upiId + "&pn=" + name + "&am=" + amount + "&cu=INR";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitMatrix matrix = new MultiFormatWriter().encode(upiLink, BarcodeFormat.QR_CODE, 300, 300);
        MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
        return stream.toByteArray();
    }

    // Get all transactions
    @GetMapping("/all")
    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }

    // Get transactions by user ID
@GetMapping("/byUser/{userId}")
public ResponseEntity<?> getTransactionsByUserId(@PathVariable Long userId) {
    try {
        // Find user by ID
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        // Fetch transactions belonging to the user
        List<Transaction> transactions = transactionRepo.findByUser(user);

        return ResponseEntity.ok(transactions);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error fetching transactions: " + e.getMessage()));
    }
}

}
