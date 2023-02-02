package oslomet.testing.Sikkerhet;

import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oslomet.testing.DAL.BankRepository;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

@RestController
public class Sikkerhet {
    @Autowired
    BankRepository rep;

    @Autowired
    private HttpSession session = new HttpSession() {
        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(int i) {

        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String s) {
            return null;
        }

        @Override
        public Object getValue(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return new String[0];
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public void putValue(String s, Object o) {

        }

        @Override
        public void removeAttribute(String s) {

        }

        @Override
        public void removeValue(String s) {

        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean isNew() {
            return false;
        }
    };

    @GetMapping("/loggInn")
    public String sjekkLoggInn(String personnummer, String passord) {
        String regexPersonnummer = "[0-9]{11}";
        String regexPassord = ".{6,30}";

        boolean personnummerOK = personnummer.matches(regexPersonnummer);
        boolean passordOK = passord.matches(regexPassord);

        if (!personnummerOK) {
            return "Feil i personnummer";
        }
        if (!passordOK) {
            return "Feil i passord";
        }

        String resultat = rep.sjekkLoggInn(personnummer, passord);
        if (resultat.equals("OK")) {
            session.setAttribute("Innlogget", personnummer);
            return "OK";
        }
        return "Feil i personnummer eller passord";
    }

    @GetMapping("/loggUt")
    public void loggUt() {
        session.setAttribute("Innlogget", null);
    }

    @GetMapping("/loggInnAdmin")
    public String loggInnAdmin(String bruker, String passord){
        if (bruker.equals("Admin") && (passord.equals(("Admin")))) {
            session.setAttribute("Innlogget", "Admin");
            return "Logget inn";
        }
        else {
            session.setAttribute("Innlogget", null);
            return "Ikke logget inn";
        }
    }

    public String loggetInn() {
        if (session.getAttribute("Innlogget") != null){
            return (String) session.getAttribute("Innlogget");
        }
        return null;
    }
}
