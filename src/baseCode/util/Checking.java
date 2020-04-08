package baseCode.util;

import java.util.Objects;

public class Checking {

    public Boolean checkCodeMeli(String melliCode){
        if(melliCode.length() == 10){
            if(Objects.equals(melliCode, "0000000000") || Objects.equals(melliCode, "1111111111") || Objects.equals(melliCode, "2222222222") || Objects.equals(melliCode, "3333333333") || Objects.equals(melliCode, "4444444444") || Objects.equals(melliCode, "5555555555") || Objects.equals(melliCode, "6666666666") || Objects.equals(melliCode, "7777777777") || Objects.equals(melliCode, "8888888888") || Objects.equals(melliCode, "9999999999")) {
                return false;
            } else {
                int c = Integer.parseInt(melliCode.substring(9));
                int thwo = Integer.parseInt(String.valueOf(melliCode.charAt(8))) * 2;
                int three = Integer.parseInt(String.valueOf(melliCode.charAt(7))) * 3;
                int four = Integer.parseInt(String.valueOf(melliCode.charAt(6))) * 4;
                int five = Integer.parseInt(String.valueOf(melliCode.charAt(5))) * 5;
                int six = Integer.parseInt(String.valueOf(melliCode.charAt(4))) * 6;
                int seven = Integer.parseInt(String.valueOf(melliCode.charAt(3))) * 7;
                int eight = Integer.parseInt(String.valueOf(melliCode.charAt(2))) * 8;
                int nine = Integer.parseInt(String.valueOf(melliCode.charAt(1))) * 9;
                int ten = Integer.parseInt(String.valueOf(melliCode.charAt(0))) * 10;
                int n = ten + nine + eight + seven + six + five + four + three + thwo;

                int r = n - (n / 11) * 11;

                if((r == 0 && r == c) || (r == 1 && c == 1) || (r > 1 && c == 11 - r)) {
                    return true;
                }
            }
        }
        return false;
    }

}
