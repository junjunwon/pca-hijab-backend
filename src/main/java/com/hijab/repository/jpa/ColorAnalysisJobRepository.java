package com.hijab.repository.jpa;

import com.hijab.colorAnalysis.ColorAnalysisJob;
import com.hijab.interview.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorAnalysisJobRepository extends JpaRepository<ColorAnalysisJob, Long> {
    List<ColorAnalysisJob> findColorAnalysisJobBy(int jobId);
}
