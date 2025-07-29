package com.mgt.controller;

import org.springframework.web.bind.annotation.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mgt.model.Transaction;
import com.mgt.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

     @Autowired
    private TransactionRepository transactionRepo;

    // Create new payment transaction
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction createPayment(@RequestParam Double amount, @RequestParam String transactionId) {
   
        String upiId = "8446898397@ybl"; // replace with your UPI ID
        String name = "Jaykumar";
        String upiLink = "upi://pay?pa=" + upiId + "&pn=" + name + "&am=" + amount + "&cu=INR";

        Transaction txn = new Transaction();
        txn.setTransactionId(transactionId);
        txn.setUpiLink(upiLink);
        txn.setAmount(amount);
        txn.setStatus("PENDING");

        return transactionRepo.save(txn);
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

}
