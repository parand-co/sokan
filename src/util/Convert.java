package util;

import java.text.NumberFormat;
import java.util.Locale;

public class Convert {

    public String numberToString(Integer num){
        if(num == null || num == 0){
            return "صفر";
        }
        String result = "";

        int sadgan;
        int dahgan;
        int yekan;

        sadgan = num / 100;
        num = num % 100;

        dahgan = num /10;
        yekan = num % 10;

        if(sadgan == 100){
            if(result.equals("")) {
                result += "صد";
            } else {
                result += " و " + "صد";
            }
        }
        if(sadgan == 200){
            if(result.equals("")) {
                result += "دویست";
            } else {
                result += " و " + "دویست";
            }
        }
        if(sadgan == 300){
            if(result.equals("")) {
                result += "سیصد";
            } else {
                result += " و " + "سیصد";
            }
        }
        if(sadgan == 400){
            if(result.equals("")) {
                result += "چهارصد";
            } else {
                result += " و " + "چهارصد";
            }
        }
        if(sadgan == 500){
            if(result.equals("")) {
                result += "پانصد";
            } else {
                result += " و " + "پانصد";
            }
        }
        if(sadgan == 600){
            if(result.equals("")) {
                result += "ششصد";
            } else {
                result += " و " + "ششصد";
            }
        }
        if(sadgan == 700){
            if(result.equals("")) {
                result += "هفتصد";
            } else {
                result += " و " + "هفتصد";
            }
        }
        if(sadgan == 800){
            if(result.equals("")) {
                result += "هشتصد";
            } else {
                result += " و " + "هشتصد";
            }
        }
        if(sadgan == 900){
            if(result.equals("")) {
                result += "نه صد";
            } else {
                result += " و " + "نه صد";
            }
        }

        if(dahgan == 90){
            if(result.equals("")) {
                result += "نود";
            } else {
                result += " و " + "نود";
            }
        }
        if(dahgan == 80){
            if(result.equals("")) {
                result += "هشتاد";
            } else {
                result += " و " + "هشتاد";
            }
        }
        if(dahgan == 70){
            if(result.equals("")) {
                result += "هفتاد";
            } else {
                result += " و " + "هفتاد";
            }
        }
        if(dahgan == 60){
            if(result.equals("")) {
                result += "شصت";
            } else {
                result += " و " + "شصت";
            }
        }
        if(dahgan == 50){
            if(result.equals("")) {
                result += "پنجاه";
            } else {
                result += " و " + "پنجاه";
            }
        }
        if(dahgan == 40){
            if(result.equals("")) {
                result += "چهل";
            } else {
                result += " و " + "چهل";
            }
        }
        if(dahgan == 30){
            if(result.equals("")) {
                result += "سی";
            } else {
                result += " و " + "سی";
            }
        }
        if(dahgan == 20){
            if(result.equals("")) {
                result += "بیست";
            } else {
                result += " و " + "بیست";
            }
        }

        if(dahgan == 10){
            if(num == 10){
                if(result.equals("")) {
                    result += "ده";
                } else {
                    result += " و " + "ده";
                }
            }
            if(num == 11){
                if(result.equals("")) {
                    result += "یازده";
                } else {
                    result += " و " + "یازده";
                }
            }
            if(num == 12){
                if(result.equals("")) {
                    result += "دوازده";
                } else {
                    result += " و " + "دوازده";
                }
            }
            if(num == 13){
                if(result.equals("")) {
                    result += "سیزده";
                } else {
                    result += " و " + "سیزده";
                }
            }
            if(num == 14){
                if(result.equals("")) {
                    result += "چهارده";
                } else {
                    result += " و " + "چهارده";
                }
            }
            if(num == 15){
                if(result.equals("")) {
                    result += "پانزده";
                } else {
                    result += " و " + "پانزده";
                }
            }
            if(num == 16){
                if(result.equals("")) {
                    result += "شانزده";
                } else {
                    result += " و " + "شانزده";
                }
            }
            if(num == 17){
                if(result.equals("")) {
                    result += "هفده";
                } else {
                    result += " و " + "هفده";
                }
            }
            if(num == 18){
                if(result.equals("")) {
                    result += "هجده";
                } else {
                    result += " و " + "هجده";
                }
            }
            if(num == 19){
                if(result.equals("")) {
                    result += "نوزده";
                } else {
                    result += " و " + "نوزده";
                }
            }
        } else {
            if(yekan == 1){
                if(result.equals("")) {
                    result += "یک";
                } else {
                    result += " و " + "یک";
                }
            }
            if(yekan == 2){
                if(result.equals("")) {
                    result += "دو";
                } else {
                    result += " و " + "دو";
                }
            }
            if(yekan == 3){
                if(result.equals("")) {
                    result += "سه";
                } else {
                    result += " و " + "سه";
                }
            }
            if(yekan == 4){
                if(result.equals("")) {
                    result += "چهار";
                } else {
                    result += " و " + "چهار";
                }
            }
            if(yekan == 5){
                if(result.equals("")) {
                    result += "پنج";
                } else {
                    result += " و " + "پنج";
                }
            }
            if(yekan == 6){
                if(result.equals("")) {
                    result += "شش";
                } else {
                    result += " و " + "شش";
                }
            }
            if(yekan == 7){
                if(result.equals("")) {
                    result += "هفت";
                } else {
                    result += " و " + "هفت";
                }
            }
            if(yekan == 8){
                if(result.equals("")) {
                    result += "هشت";
                } else {
                    result += " و " + "هشت";
                }
            }
            if(yekan == 9){
                if(result.equals("")) {
                    result += "نه";
                } else {
                    result += " و " + "نه";
                }
            }
        }

        return result;
    }

    public String clockStr(Integer saat, Boolean edited){
        if(saat != null){
            int a = saat / 100;
            int b = saat % 100;

            if(edited){
                if (b == 0)
                    return "* " + a + ":" + b + b;
                else
                    return "* " + a + ":" + b;
            } else {
                if (b == 0)
                    return a + ":" + b + b;
                else
                    return a + ":" + b;
            }
        }
        return null;
    }

    public Integer clockInt(String saat){
        if(saat != null && !saat.equals("")){

            return Integer.parseInt(saat.replaceAll(":", ""));
        }
        return null;
    }

    public Integer sumSaat(Integer vorod, Integer khoroj){
        if(vorod == null){
            vorod = 0;
        }

        if(khoroj == null){
            khoroj = 0;
        }

        String vo = String.valueOf(vorod);

        if(vo.length() == 1){
            vo = "000" + vorod;
        }
        if(vo.length() == 2){
            vo = "00" + vorod;
        }
        if(vo.length() == 3){
            vo = "0" + vorod;
        }

        String kh = String.valueOf(khoroj);
        if(kh.length() == 1){
            kh = "000" + khoroj;
        }
        if(kh.length() == 2){
            kh = "00" + khoroj;
        }
        if(kh.length() == 3){
            kh = "0" + khoroj;
        }

        String saatVorod = "";
        String daghigheVorod = "";
        if(vo.length() == 4){
            saatVorod = vo.substring(0,2);
            daghigheVorod = vo.substring(2,4);
        } else if(vo.length() == 5){
            saatVorod = vo.substring(0,3);
            daghigheVorod = vo.substring(3,5);
        }

        String saatKhoroj = "";
        String daghigheKhoroj = "";
        if(kh.length() == 4){
            saatKhoroj = kh.substring(0,2);
            daghigheKhoroj = kh.substring(2,4);
        } else if(kh.length() == 5){
            saatKhoroj = kh.substring(0,3);
            daghigheKhoroj = kh.substring(3,5);
        }

        int finalSaat = Integer.valueOf(saatVorod) + Integer.valueOf(saatKhoroj);
        int finalDaghighe = Integer.valueOf(daghigheVorod) + Integer.valueOf(daghigheKhoroj);

        if(finalDaghighe > 60){
            finalSaat += 1;
            finalDaghighe -= 60;
        }

        String result;
        if(finalSaat < 10){
            result = "0" + finalSaat;
        } else {
            result = String.valueOf(finalSaat);
        }
        if(finalDaghighe < 10){
            result += "0" + finalDaghighe;
        } else {
            result += finalDaghighe;
        }

        return Integer.valueOf(result);
    }

    public Integer minesSaat(Integer angost, String asli, boolean vorod){
        if(angost == null){
            angost = 0;
        }
        if(asli == null || asli.equals("")){
            asli = "0";
        }
        if(asli.length() == 1){
            asli = "000" + angost;
        }
        if(asli.length() == 2){
            asli = "00" + angost;
        }
        if(asli.length() == 3){
            asli = "0" + angost;
        }
        String saatAsli = asli.substring(0,2);
        String daghigheAsli = asli.substring(2,4);

        String saat = String.valueOf(angost);
        if(saat.length() == 1){
            saat = "000" + angost;
        }
        if(saat.length() == 2){
            saat = "00" + angost;
        }
        if(saat.length() == 3){
            saat = "0" + angost;
        }

        String saatKari = saat.substring(0,2);
        String daghigheKari = saat.substring(2,4);

        int finalSaat;
        int finalDaghighe;

        if(vorod){
            finalSaat = Integer.valueOf(saatKari) - Integer.valueOf(saatAsli);
            finalDaghighe = Integer.valueOf(daghigheKari) - Integer.valueOf(daghigheAsli);
        } else {
            finalSaat = Integer.valueOf(saatAsli) - Integer.valueOf(saatKari);
            finalDaghighe = Integer.valueOf(daghigheAsli) - Integer.valueOf(daghigheKari);
        }

        if(finalDaghighe < 0){
            finalSaat -= 1;
            finalDaghighe += 60;
        }

        String fd;
        if(finalDaghighe == 0){
            fd = "00";
        } else {
            fd = String.valueOf(finalDaghighe);
        }

        Integer result = Integer.valueOf(finalSaat + fd);

        if(result > 0){
            return result;
        } else {
            return 0;
        }
    }

    public String separatorThousands(Float input){
        if(input != null){
            return NumberFormat.getNumberInstance(Locale.US).format(input);
        }
        return "";
    }
}