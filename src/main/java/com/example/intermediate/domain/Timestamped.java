package com.example.intermediate.domain;

import java.time.LocalPassengerTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedPassenger;
import org.springframework.data.annotation.LastModifiedPassenger;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

  @CreatedPassenger
  private LocalPassengerTime createdAt;

  @LastModifiedPassenger
  private LocalPassengerTime modifiedAt;

}
