package com.mgt.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import java.nio.file.Path;



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

   @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> createPayment(@RequestParam String title,
                                       @RequestParam Double amount,
                                       @RequestParam String description,
                                       @RequestParam String transactionId,
                                       @RequestParam("screenshot") MultipartFile screenshot,  // new param
                                       @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
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
        txn.setTitel(title);
        txn.setDescription(description);

        //  Save the uploaded screenshot file
        if (screenshot != null && !screenshot.isEmpty()) {
            String uploadDir = "uploads/screenshots";  // You can change this path
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = transactionId + "_" + screenshot.getOriginalFilename();
            Path filePath = Paths.get(uploadDir).resolve(fileName);

            Files.copy(screenshot.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Optional: save the filename or path to the transaction
            txn.setImString(filePath.toString());
        }

        transactionRepo.save(txn);

        user.setPaymentStatus(true);
        userRepo.save(user);

        return ResponseEntity.ok(Map.of("message", "Transaction saved successfully"));

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while creating transaction: " + e.getMessage());
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

// Get uploaded screenshot image by transaction ID
@GetMapping(value = "/image/{transactionId}", produces = MediaType.IMAGE_JPEG_VALUE)
public ResponseEntity<byte[]> getImageByTransactionId(@PathVariable String transactionId) {
    try {
        Transaction txn = transactionRepo.findByTransactionId(transactionId);
        if (txn == null || txn.getImString() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        Path imagePath = Paths.get(txn.getImString());
        if (!Files.exists(imagePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);
        String contentType = Files.probeContentType(imagePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(imageBytes);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
    }
}


}
