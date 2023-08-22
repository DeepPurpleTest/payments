package com.example.payments.controller.client;

import com.example.payments.entity.Payment;
import com.example.payments.service.PaymentService;
import com.example.payments.service.PdfGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.payments.entity.Receipt.TEMPLATE_1;

@RestController
@RequestMapping("/client/receipt")
@RequiredArgsConstructor
public class ClientReceiptController {
    private final PdfGenerationService pdfGenerationService;
    private final PaymentService paymentService;
    @GetMapping(value = "/get/{id}", produces = "application/pdf")
    public ResponseEntity<byte[]> getReceipt(@PathVariable("id") Long id) {
        Payment payment = paymentService.findById(id);
        String stringHtml = pdfGenerationService.getStringHtml(TEMPLATE_1.templateName, payment);
        byte[] bytes = pdfGenerationService.generatePdfFromHtml(stringHtml);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=receipt.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}
