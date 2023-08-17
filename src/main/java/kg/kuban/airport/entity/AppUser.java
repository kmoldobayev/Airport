package kg.kuban.airport.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "user_password")
    private String userPassword;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @Transient
    private List<GrantedAuthority> authorities;

    @Column(name = "date_begin")
    private LocalDate dateBegin;
    @Column(name = "date_ending")
    private LocalDate dateEnding;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m2m_user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AppRole> appRoles;

    @OneToOne(mappedBy = "servicedBy")
    private Airplane servicedAirplane;

    @PrePersist
    public void prePersist() {
        this.setDateBegin(LocalDate.now());
        this.setEnabled(true);
    }

    public AppUser() {
        this.appRoles = new HashSet<>();
    }

    public AppUser(String userLogin, String userPassword, List<GrantedAuthority> authorities) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.appRoles;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userLogin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Long getId() {
        return id;
    }

    public AppUser setId(Long id) {
        this.id = id;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public AppUser setPosition(Position position) {
        this.position = position;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public AppUser setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public AppUser setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public AppUser setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }


    public Set<AppRole> getAppRoles() {
        return appRoles;
    }

    public AppUser setAppRoles(Set<AppRole> appRoles) {
        this.appRoles = appRoles;
        return this;
    }

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public AppUser setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
        return this;
    }

    public LocalDate getDateEnding() {
        return dateEnding;
    }

    public AppUser setDateEnding(LocalDate dateEnding) {
        this.dateEnding = dateEnding;
        return this;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public AppUser setEnabled(Boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    public Airplane getServicedAirplane() {
        return servicedAirplane;
    }

    public AppUser setServicedAirplane(Airplane servicedAirplane) {
        this.servicedAirplane = servicedAirplane;
        return this;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", login='" + userLogin + '\'' +
                ", password='" + userPassword + '\'' +
                ", role=" + this.getAppRoles().stream().map( x->x.getTitle()).collect(Collectors.joining(", ")) +
                '}';
    }
}
