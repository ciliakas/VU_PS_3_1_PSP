package validator;

public class PasswordChecker {
    public boolean isPasswordValid(String password, int length, String specialSymbols) throws IllegalArgumentException{
        validateRequirements(length, specialSymbols);
        return password != null &&
                validateLength(password, length) &&
                validateUppercase(password) &&
                Utils.containsSymbol(password, specialSymbols);
    }

    private boolean validateLength(String password, int length) {
        return length > 0 && password.length() >= length;
    }

    private boolean validateUppercase(String password) {
        return !password.toLowerCase().equals(password);
    }

    private void validateRequirements(int length, String specialSymbols) throws IllegalArgumentException{
        if(length <= 0){
            throw new IllegalArgumentException("Length requirement must be more than 0");
        }
        if(specialSymbols == null){
            throw new IllegalArgumentException("Special symbol requirement must not be null");
        }
        if(specialSymbols.isEmpty()){
            throw new IllegalArgumentException("Special symbol requirement must not be empty");
        }
    }
}
