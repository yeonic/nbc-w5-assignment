package me.yeon.nbcw5assignment.domain.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.yeon.nbcw5assignment.domain.BaseEntity;
import me.yeon.nbcw5assignment.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(length = 3500)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Schedule(User author, String title, String content) {
    this.user = author;
    this.title = title;
    this.content = content;
  }

  public void updateSchedule(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
