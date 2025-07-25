//package project.project.Security;
//
//
//
//
//
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//public class CustomAuthenticationToken extends AbstractAuthenticationToken {
//    private final Object principal;
//    private Object credentials;
//
//    public CustomAuthenticationToken(Object principal, Object credentials) {
//        super(null);
//        this.principal = principal;
//        this.credentials = credentials;
//        setAuthenticated(false);
//    }
//
//    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//        this.principal = principal;
//        this.credentials = credentials;
//        super.setAuthenticated(true);
//    }
//
//    @Override
//    public Object getCredentials() {
//        return this.credentials;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return this.principal;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        super.setAuthenticated(isAuthenticated);
//    }
//
//    @Override
//    public void eraseCredentials() {
//        super.eraseCredentials();
//        this.credentials = null;
//    }
//}