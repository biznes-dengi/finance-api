package com.finance.app.goal.domain;

import com.finance.app.goal.domain.enums.ImageType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Base64;

@Data
@NoArgsConstructor
@Embeddable
public class ImageGoal {
    @Column(name = "type_image")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ImageType type;
    @Column(name = "image")
    private byte[] value;

    public ImageGoal(ImageType type, String value) {
        this.type = type;
        this.setValue(value);
    }

    public String getValue() {
        return Base64.getEncoder().encodeToString(this.value);
    }

    public void setValue(String image) {
        this.value = Base64.getDecoder().decode(image);
    }
}
