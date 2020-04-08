function tarihkPayan(shoro, modat, payan) {
    var sh = document.getElementById(shoro).value.split("/");
    var md = document.getElementById(modat).value;

    var sal = parseInt(sh[0]);
    var mah = parseInt(sh[1]);
    var roz = parseInt(sh[2]);

    var condition = 0;
    if(md === "" || document.getElementById(shoro).value === ""){
        condition = 1;
        document.getElementById(payan).value = "";
        sal = parseInt(sh[0]);
        mah = parseInt(sh[1]);
        roz = parseInt(sh[2]);
    }

    while (condition === 0){
        md -= 1;
        roz += 1;
        if(mah > 6){
            if(roz > 30){
                roz = 1;
                mah += 1;
            }
        } else {
            if(roz > 31){
                roz = 1;
                mah += 1;
            }
        }

        if(mah > 12){
            mah = 1;
            sal += 1;
        }

        if(md === 0){
            condition = 1;

            if (mah < 10) {
                mah = "0" + mah;
            }

            if(roz < 10){
                roz = "0" + roz;
            }

            document.getElementById(payan).value = sal + "/" + mah + "/" + roz;
        }
    }
}