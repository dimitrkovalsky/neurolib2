package com.liberty.service.impl;

import com.liberty.error.OperationFailedException;
import org.owasp.validator.html.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by user on 31.05.2017.
 */
@Service
public class XSSFilterImpl {

    @Autowired
    private AntiSamy antiSamy;

    @Autowired
    private Policy policy;

    public String cleanHTML(String message) {
        // As a convenience for the caller and to avoid an error, if the message is null, return an empty string
        // to avoid an exception.
        if (message == null) {
            return "";
        }
        try {
            CleanResults cr = antiSamy.scan(message, policy);
            return cr.getCleanHTML();
        } catch (ScanException e) {
            throw new OperationFailedException("Error cleansing message");
        } catch (PolicyException e) {
            throw new OperationFailedException("Error with AntiSamy policy exception");
        }
    }
}
