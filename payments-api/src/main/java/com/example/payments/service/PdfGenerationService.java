package com.example.payments.service;

import com.example.payments.entity.Payment;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {
    private final TemplateEngine templateEngine;
    private final ConverterProperties converterProperties;

    public String getStringHtml(String html, Payment payment) {
        Context context = new Context();
        context.setVariable("payment", payment);
        context.setVariable("sender", payment.getSender().getUser());
        context.setVariable("receiver", payment.getReceiver().getUser());

        return templateEngine.process(html, context);
    }

    public byte[] generatePdfFromHtml(String orderHtml) {
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        converterProperties.setBaseUri(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        return target.toByteArray();
    }

}
