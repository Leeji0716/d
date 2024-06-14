package com.example.MMP.lesson;

import com.example.MMP.siteuser.SiteUser;
import com.example.MMP.siteuser.SiteUserRepository;
import com.example.MMP.siteuser.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final SiteUserRepository siteUserRepository;

    public void create(String lessonName, String headCount, LocalDate lessonDate, LocalTime startTime, LocalTime endTime, SiteUser trainer) {
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonName);
        lesson.setHeadCount(Integer.parseInt(headCount));
        lesson.setLessonDate(lessonDate);
        lesson.setStartTime(startTime);
        lesson.setEndTime(endTime);
        lesson.setTrainer(trainer);

        lessonRepository.save(lesson);
    }

    public List<Lesson> getLessonFromDate(LocalDate localDate) {
        List<Lesson> lessonList = lessonRepository.findByLessonDate(localDate);
        return lessonList;
    }

    public Lesson getLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id).get();
        return lesson;
    }

    @Transactional
    public void reservation(Lesson lesson, SiteUser siteUser) {
        lesson.getAttendanceList().add(siteUser);
        lessonRepository.save(lesson);
        siteUser.getLessonList().add(lesson);
        siteUserRepository.save(siteUser);
        System.out.println("Lesson attendance list size after saving: " + lesson.getAttendanceList().size());
        for(SiteUser siteUser1 : lesson.getAttendanceList()){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + siteUser.getName());
        }
    }
}
