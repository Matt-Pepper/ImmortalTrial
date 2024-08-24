package com.immortaltrial.game.users.tokens;

import com.immortaltrial.game.users.User;
import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter private String token;

    @Getter
    @Setter
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    @Getter @Setter private Date expiryDate;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(final String token) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public PasswordResetToken(final String token, final User user) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExpiryDate() == null) ? 0 : getExpiryDate().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PasswordResetToken other = (PasswordResetToken) obj;
        return Objects.equals(getExpiryDate(), other.getExpiryDate())
                && Objects.equals(getToken(), other.getToken())
                && Objects.equals(getUser(), other.getUser());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=")
                .append(token)
                .append("]")
                .append("Expires")
                .append(expiryDate)
                .append("]");
        return builder.toString();
    }
}
