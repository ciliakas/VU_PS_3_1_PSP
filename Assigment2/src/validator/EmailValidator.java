package validator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EmailValidator {
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!#$%&'*+-/=?^_`{|}~.";
    private static final String ALLOWED_LOCAL_PART_SYMBOLS = LOWERCASE_LETTERS + UPPERCASE_LETTERS + DIGITS + SPECIAL_CHARS;
    private static final String ALLOWED_DOMAIN_PART_SYMBOLS = LOWERCASE_LETTERS + UPPERCASE_LETTERS + DIGITS + ".-";
    private final String[] tldList;

    public EmailValidator() {
        String[] tldList;
        try {
            tldList = readTLDList();
        } catch (IOException e) {
            tldList = new String[0];
            e.printStackTrace();
        }
        this.tldList = tldList;
    }

    public boolean validate(String emailAddress) {
        if (emailAddress == null || !validateAt(emailAddress)) {
            return false;
        }
        var splitEmail = splitEmailIntoLocalAndDomain(emailAddress);
        return validateLocalPart(splitEmail[0]) && validateDomainPart(splitEmail[1]);
    }

    private boolean validateLocalPart(String localPart) {
        return validateLocalDots(localPart) && Utils.containsOnly(localPart, ALLOWED_LOCAL_PART_SYMBOLS);
    }

    private boolean validateLocalDots(String localPart) {
        var indexes = Utils.getIndexes(localPart, '.');
        return indexes.isEmpty() ||
                (!indexes.contains(0) && !indexes.contains(localPart.length() - 1) && Utils.checkForNoTouchingIndexes(indexes));
    }

    private boolean validateDomainPart(String domainPart) {
        if (!validateDomainDots(domainPart) || !Utils.containsOnly(domainPart, ALLOWED_DOMAIN_PART_SYMBOLS)) {
            return false;
        }
        var subDomains = splitIntoSubDomains(domainPart);
        if (Arrays.stream(subDomains).anyMatch(subDomain -> !validateDomainHyphens(subDomain))) {
            return false;
        }
        return validateTld(subDomains[subDomains.length - 1]);
    }

    private boolean validateTld(String tld){
        return tldList.length == 0 || Arrays.asList(tldList).contains(tld);
    }

    private boolean validateDomainDots(String domainPart) {
        var indexes = Utils.getIndexes(domainPart, '.');
        return indexes.size() != 0
                && !indexes.contains(0) && !indexes.contains(domainPart.length() - 1) && Utils.checkForNoTouchingIndexes(indexes);
    }

    private boolean validateDomainHyphens(String domainPart) {
        var indexes = Utils.getIndexes(domainPart, '-');
        return !indexes.contains(0) && !indexes.contains(domainPart.length() - 1);
    }

    private boolean validateAt(String emailAddress) {
        if (emailAddress.chars().filter(ch -> ch == '@').count() != 1) {
            return false;
        }
        var atIndex = emailAddress.indexOf('@');
        return atIndex > 0 && atIndex < emailAddress.length();
    }

    private String[] splitEmailIntoLocalAndDomain(String emailAddress) {
        var atIndex = emailAddress.indexOf('@');
        return new String[]{emailAddress.substring(0, atIndex), emailAddress.substring(atIndex + 1)};
    }

    private String[] splitIntoSubDomains(String domain) {
        var indexes = Utils.getIndexes(domain, '.');
        var subDomains = new ArrayList<String>();
        var indexCount = indexes.size();
        subDomains.add(domain.substring(0, indexes.get(0)));
        for (var i = 0; i < indexCount; i++) {
            subDomains.add(i == indexCount - 1 ?
                    domain.substring(indexes.get(i) + 1) : domain.substring(indexes.get(i) + 1, indexes.get(i + 1)));
        }
        return subDomains.toArray(new String[0]);
    }

    private String[] readTLDList() throws IOException {
        InputStream in = getClass().getResourceAsStream("/tld.csv");
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(in));
        var tldList = new ArrayList<String>();
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            tldList.add(data[0]);
        }
        csvReader.close();
        return tldList.toArray(new String[0]);
    }
}
