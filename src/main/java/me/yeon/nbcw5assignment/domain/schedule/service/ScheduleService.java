package me.yeon.nbcw5assignment.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.dao.CommentRepository;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import me.yeon.nbcw5assignment.domain.schedule.dao.ScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.dto.ScheduleDto;
import me.yeon.nbcw5assignment.domain.schedule.dto.ScheduleMapper;
import me.yeon.nbcw5assignment.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository repository;
  private final CommentRepository commentRepository;

  public ScheduleDto.Res addSchedule(User author, String title, String content) {
    Schedule schedule = Schedule.builder()
        .author(author)
        .title(title)
        .content(content)
        .build();
    return ScheduleMapper.toRes(repository.save(schedule), 0L);
  }

  public ScheduleDto.Res updateSchedule(Long scheduleId, String title, String content) {
    Schedule schedule = repository.findById(scheduleId).orElseThrow();
    String updateTitle = title == null ? schedule.getTitle() : title;
    String updateContent = content == null ? schedule.getContent() : content;

    Schedule updatedSchedule = repository.update(scheduleId, updateTitle, updateContent);
    Long commentCount = commentRepository.countAllByScheduleId(updatedSchedule.getId());

    return ScheduleMapper.toRes(updatedSchedule, commentCount);
  }

  public ScheduleDto.Res getSchedule(Long scheduleId) {
    Schedule schedule = repository.findById(scheduleId).orElseThrow();
    Long commentCount = commentRepository.countAllByScheduleId(schedule.getId());
    return ScheduleMapper.toRes(schedule, commentCount);
  }

  public Page<ScheduleDto.Res> getSchedules(int pageNo, int pageSize, String criteria) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, criteria));
    return repository.findAll(pageable).<ScheduleDto.Res>map(schedule -> {
      Long commentCount = commentRepository.countAllByScheduleId(schedule.getId());
      return ScheduleMapper.toRes(schedule, commentCount);
    });
  }

  public Page<ScheduleDto.Res> getSchedulesOfUser(Long userId, int pageNo, int pageSize,
      String creteria) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, creteria));

    return repository.findAllByUserId(userId, pageable).map(schedule -> {
      Long commentCount = commentRepository.countAllByScheduleId(schedule.getId());
      return ScheduleMapper.toRes(schedule, commentCount);
    });
  }

  public void deleteSchedule(Long scheduleId) {
    repository.delete(scheduleId);
  }
}
