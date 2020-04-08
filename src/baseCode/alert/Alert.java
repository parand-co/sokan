package baseCode.alert;

import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Alert {

    public Alert() {
    }

    public Alert(String header,String text) {
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(header,text));
    }

    public void warningAlert(String header,String text) {
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(header,text));
    }

    public void successSave(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("صحیح","ثبت با موفقیت انجام شد"));
    }
    public void successEdit(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("صحیح","ویرایش با موفقیت انجام شد"));
    }
    public void successDelete(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("صحیح","حذف با موفقیت انجام شد"));
    }

    public void unSuccessSave(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","ثبت انجام نشد"));
    }
    public void unSuccessEdit(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","ویرایش انجام نشد"));
    }
    public void unSuccessDelete(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","حذف انجام نشد"));
    }
    public void unSuccessSearch(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","موردی یافت نشد"));
    }
    public void firstSelectItem(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","حداقل یکی از موارد را انتخاب یا وارد نمایید"));
    }
    public void firstSearchPersonel(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","لطفا ابتدا یک پرسنل را جستجو نمایید"));
    }
    public void startDateIsBiger(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","تاریخ شروع بزرگتر از تاریخ پایان است"));
    }
    public void fieldIsEmpty(String field){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا",field +" نمیتواند خالی باشد."));
    }
    public void allFeildNecessery(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا"," پر نمودن تمامی فیلدها الزامی میباشد."));
    }
    public void notFoundPersonel(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","پرسنل مورد نظر یافت نشد."));
    }
    public void notFoundList(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","موردی یافت نشد."));
    }
    public void error(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","مشکلی پیش آمده است."));
    }
    public void userDuplicate(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","نام کاربری تکراری می باشد."));
    }
    public void codeDuplicate(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","کد تکراری می باشد."));
    }
    public void fieldPass(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","کلمه عبور اشتباه است."));
    }
    public void fieldEqualPass(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","کلمه های عبور جدید همسان نیستند."));
    }
    public void fieldValidPass(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","کلمه عبور استاندارد نمی باشد."));
    }
    public void neseceryFillField(){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("خطا","حداقل یکی از فیلدها باید پرشوند"));
    }
    public void successRecoveryPassword(String username){
        RequestContext context = RequestContext.getCurrentInstance();
        String text = "جناب " + username + " ، کلمه عبور شما با موفقیت تعویض گردید.";
        context.execute("swal('موفقیت', '" + text + " ', 'success')");
    }
    public void faildValidPassword(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('خطا', 'کلمه عبور استاندارد نمی باشد', 'error')");
    }
    public void notSetPassword(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('خطا', 'کلمه های عبور همانند یکدیگر نیستند', 'error')");
    }
    public void wrongAnswer(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('خطا', 'پاسخ ها صحیح نمی باشند', 'error')");
    }
    public void selectQuestion(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('هشدار', 'لطفا سوالات را انتخاب نمایید و به دقت به همه آنها پاسخ دهید', 'warning')");
    }
    public void wrongUser(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('خطا', 'کاربر مورد نظر معتبر نمی باشد', 'error')");
    }
    public void wrongCaptcha(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('خطا', 'حروف تصویر را اشتباه وارد کردید', 'error')");
    }
    public void loginSuccess(String username){
        RequestContext context = RequestContext.getCurrentInstance();
        String text = "جناب " + username + " ، به سامانه جامع مدیریت نیروی انسانی خوش آمدید.";
        context.execute("swal('موفقیت', '" + text + " ', 'success')");
    }
    public void error(String error){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('خطا', '" + error + "', 'error')");
    }
    public void success(String success){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('موفقیت', '" + success + "', 'success')");
    }
    public void warning(String warning){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("swal('هشدار', '" + warning + "', 'warning')");
    }
}
