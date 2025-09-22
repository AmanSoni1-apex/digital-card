package com.example.hotelcard.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRCodeGenerator {
    public static byte[] generateQRCodeImage(String text , int width, int height) throws WriterException , IOException{
        QRCodeWriter qrcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrcodeWriter .encode(text , BarcodeFormat.QR_CODE,width ,height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"PNG",pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
