package me.yeon.nbcw5assignment.domain.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import me.yeon.nbcw5assignment.domain.BaseEntity;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import me.yeon.nbcw5assignment.domain.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 650)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @ManyToOne
  @JoinColumn(name = "schedule_id")
  Schedule schedule;

  @Builder
  public Comment(Long id, String content, User user, Schedule schedule) {
    this.id = id;
    this.content = content;
    this.user = user;
    this.schedule = schedule;
  }

  public void updateComment(String content) {
    this.content = content;
  }
}
