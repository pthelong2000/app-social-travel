package app.chatsservice.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public abstract class AbstractEntity {

    @CreatedBy
    protected String createdBy;

    @CreatedDate
    protected LocalDateTime createAt;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    protected LocalDateTime lastModifiedAt;

    public abstract LocalDateTime getCreatedAt();

    public abstract String getCreatedBy();

    public abstract LocalDateTime getLastModifiedAt();

    public abstract String getLastModifiedBy();
}
