package com.example.payments.controller.client;

import com.example.payments.dto.OutPaymentDto;
import com.example.payments.entity.Payment;
import com.example.payments.service.PaymentService;
import com.example.payments.service.PdfGenerationService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.GenericMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.payments.entity.Receipt.TEMPLATE_1;

@RestController
@RequestMapping("/client/receipt")
@RequiredArgsConstructor
public class ClientReceiptController {
    private final PdfGenerationService pdfGenerationService;
    private final PaymentService paymentService;
    private final GenericMapper<Payment, OutPaymentDto> paymentMapper;

    @PostMapping(value = "/_get", produces = "application/pdf")
    public ResponseEntity<byte[]> getReceipt(@RequestBody @Valid OutPaymentDto outPaymentDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Invalid data", bindingResult);
        }

        Payment payment = paymentService.findById(paymentMapper.toEntity(outPaymentDto));
        String stringHtml = pdfGenerationService.getStringHtml(TEMPLATE_1.templateName, payment);
        byte[] bytes = pdfGenerationService.generatePdfFromHtml(stringHtml);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=receipt.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}
