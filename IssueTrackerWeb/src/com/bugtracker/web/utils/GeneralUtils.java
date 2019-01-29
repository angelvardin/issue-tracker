package com.bugtracker.web.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.util.encoders.Hex;

import com.bugtracker.entity.UserModel;



/**
 * Utility class for helping various operations
 * 
 */
public class GeneralUtils {
    
    public static UserModel getLoggedUser(Object request) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            UserModel loggedUser = (UserModel) req.getSession().getAttribute("LOGGED_USER");
            return loggedUser;
        }
        return null;
    }
    
    public static String encodeSha256Password(String aPlainTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(aPlainTextPassword.getBytes("UTF-8"));
            return new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No encoding algorythm found", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("No encoding support", e);
        }
    }
    
    public static String encodeMd5(String aPlainText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(aPlainText.getBytes("UTF-8"), 0, aPlainText.length());
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No encoding algorythm found", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("No encoding support", e);
        }
    }
    
    
    
    public static String covertDateToString(Date aDate, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(aDate);
    }
    
    
}

