package com.app.pdfDecoder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class PdfController {

    @PostMapping("/decode")
    public ResponseEntity<?> decodePdf(@RequestParam("base64Pdf") String base64Pdf){

        if(base64Pdf == null || base64Pdf.trim().isEmpty()){

            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Error: Base64 string cannot be empty.");
        }
        try{
            byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "decoded.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Error: Invalid Base64 input. Please check your string.".getBytes());
        }
    }
}
