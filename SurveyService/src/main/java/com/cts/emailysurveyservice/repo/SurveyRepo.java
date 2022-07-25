package com.cts.emailysurveyservice.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.emailysurveyservice.model.Survey;

/**
 * Survey Repository
 */
@Repository
public interface SurveyRepo extends JpaRepository<Survey, Integer> {

	List<Survey> findByUserId(Integer userId);

	List<Survey> findByDateSent(Date dateSent);

	Survey findByIdAndUserId(Integer surveyId, Integer userId);

	@Modifying
	@Transactional
	@Query("update Survey set responded=:val where id=:id and userId=:userId")
	Integer updateResponded(Integer userId, @Param("id") Integer surveyId, Boolean val);

}
